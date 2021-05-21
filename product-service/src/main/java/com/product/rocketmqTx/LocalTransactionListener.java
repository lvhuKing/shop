package com.product.rocketmqTx;

import com.alibaba.fastjson.JSON;
import com.product.entity.TransferDetail;
import com.product.entity.TransferDto;
import com.product.mapper.TransferDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* @Description 作用: RocektMQ发送半消息时，调用重写的监听器的executeLocalTransaction(Message msg, Object arg)执行本地事务
* @Author ccl
* @CreateDate 2021/3/22 14:21
**/
@Slf4j
@Component
@RocketMQTransactionListener
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LocalTransactionListener implements RocketMQLocalTransactionListener {
    
    @Resource
    private UserService userServiceImpl;
    @Resource
    private TransferDetailMapper transferDetailMapper;
    
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String jsonStr = new String((byte[]) msg.getPayload());
        log.error("arg:" + arg + "执行本地事务：" + JSON.toJSONString(msg) + "，传输字段：" + jsonStr);

        TransferDto transferDto = JSON.parseObject(jsonStr, TransferDto.class);

        /**转账金额*/
        boolean flag = userServiceImpl.transferMoney(transferDto.getUserId(), transferDto.getMoney(), transferDto.getDistributedId().toString());
        // 在提交本地事务到return期间，可能因为生产者异常或网络等问题，导致MQ未接收到半消息的状态，RocketMQ机制是:后续会调用 checkLocalTransaction 检查本地事务的执行情况
        if (flag) {
            log.warn("executeLocalTransaction本地事务执行完成，提交：" + JSON.toJSONString(msg));
            // 说明本地事务执行成功,事务消息提交
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            log.warn("executeLocalTransaction 本地事务执行失败，ROLLBACK");
            // 本地事务执行失败，事务消息回滚
            return RocketMQLocalTransactionState.ROLLBACK;
            // return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String jsonStr = new String((byte[]) msg.getPayload());
        TransferDto transferDto = JSON.parseObject(jsonStr, TransferDto.class);
        TransferDetail transferDetail = transferDetailMapper.getByMsgId(transferDto.getDistributedId().toString());
        if (ObjectUtils.isNotEmpty(transferDetail)) {
            log.warn("检查方法：本地事务执行完成，提交：" + JSON.toJSONString(msg));
            if("1".equals(transferDetail.getDeleteFlg())){
                // 说明本地事务执行成功,事务消息提交
                return RocketMQLocalTransactionState.COMMIT;
            }else{
                // 本地事务执行失败，事务消息回滚
                log.warn("检查方法：回滚1");
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        } else {
            // 本地事务执行失败，事务消息回滚
            log.warn("检查方法：回滚2");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
