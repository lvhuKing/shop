package com.ccl.producer;

import com.alibaba.fastjson.JSON;
import com.ccl.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class ProducerDemo {
    
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer sendMessageTimeout;
    
    /**直接注入，用于发送消息到broker服务器*/
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String msg){
        rocketMQTemplate.send(Const.topic+":"+Const.tag_shop, MessageBuilder.withPayload(msg).setHeader(RocketMQHeaders.KEYS,"key1").build());
        log.info("生产者发送普通消息");
    }  
    
    public void syncSendMsg(String msg){
        SendResult sendResult = rocketMQTemplate.syncSend(Const.topic+":"+Const.tag_shop, MessageBuilder.withPayload(msg).build());
        log.info("生产者发送同步消息结果："+ JSON.toJSONString(sendResult));
    }
    
    public void asyncSendMsg(String msg){
        rocketMQTemplate.asyncSend(Const.topic + ":" + Const.tag_order, MessageBuilder.withPayload(msg).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("消息发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                log.info("消息发送失败");
            }
        });
    }

    /** delayLevel: 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h*/
    public void delaySyncSend(String msg){
        rocketMQTemplate.syncSend(Const.topic + ":" + Const.tag_chat, MessageBuilder.withPayload(msg).build(), sendMessageTimeout, 3);
        log.info("发送延时消息");
    }
    
    /**
     * 直接发送：
     * 发送单向消息，存在丢失消息风险，发送完消息后能直接返回，不会等待broker返回消息是否已完成发送
     * 不关心发送结果，适用于不重要的消息发送，如日志收集
     */
    public void sendOneWay(String msg){
        rocketMQTemplate.sendOneWay(Const.topic + ":" + Const.tag_chat, MessageBuilder.withPayload(msg).build());
        log.info("发送单向消息");
    }
    
}
