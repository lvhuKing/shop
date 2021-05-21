package com.order.rocketmqTx;

import com.alibaba.fastjson.JSON;
import com.product.entity.TransferDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RocketMQMessageListener(topic = "transfer-topic", selectorExpression = "toHx", consumerGroup = "group-tx-consumer")
public class ConsumerRocketMQListener implements RocketMQListener<MessageExt> {
    
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userServiceImpl;
    
    @Override
    public void onMessage(MessageExt message) {
        String key = null;
        String value = null;
        try {
            // 构建存储redis的key、value，目的是为了保证消息不会被重复消费
            String msgId = message.getMsgId();
            TransferDto transferDto = JSON.parseObject(new String(message.getBody()), TransferDto.class);
            key = "mq:" + transferDto.getDistributedId();
            log.warn("获取到当前消息的msgId:" + msgId);
            value = Thread.currentThread().getId() + ":" + System.currentTimeMillis();
            // 加分布式锁
            boolean flag = redisTemplate.opsForValue().setIfAbsent(key, value, 1, TimeUnit.HOURS);
            if (flag) {
                try {
                    /** 正常业务流程执行，完成后该消息会自动完成，期间有其他消费者执行该消息，也无法拿到锁 */
                    flag = userServiceImpl.transferMoney(transferDto.getTarMobile(), transferDto.getMoney());
                    if (!flag) {
                        throw new RuntimeException("没有添加金额成功，抛出异常");
                    }
                    log.warn("成功消费");
                } catch (Exception e) {
                    /**
                     * 执行业务出现异常，释放分布式锁，通过value验证，保证不会错误的释放锁
                     * 通过watch机制保证原子性操作，若watch被打断，则说明该key已经被修改，当然也就无需当前线程释放锁
                     */
                    redisTemplate.watch(key);
                    redisTemplate.multi();
                    String lockValue = (String) redisTemplate.opsForValue().get(key);
                    if (StringUtils.isNotBlank(lockValue) && lockValue == value) {
                        redisTemplate.delete(key);
                    }
                    redisTemplate.exec();
                    throw new RuntimeException("释放分布式锁，因消费失败，故抛出异常");
                }
            } else {
                // 未拿到锁，抛出异常，则该消息便不会被成功消费
                throw new RuntimeException("未拿到锁，不进行消费");
            }
        } catch (Exception e) {
            log.warn("消费异常，信息："+e.getMessage());
            throw new RuntimeException("消费异常");
        }
    }

}
