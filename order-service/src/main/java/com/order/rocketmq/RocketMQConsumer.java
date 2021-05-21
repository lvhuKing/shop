package com.order.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "topic_test", selectorExpression = "*", consumerGroup = "test1")
public class RocketMQConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        String msg = new String(body);
        log.info("客户端RocketMQConsumer收到消息："+msg);
    }
}
