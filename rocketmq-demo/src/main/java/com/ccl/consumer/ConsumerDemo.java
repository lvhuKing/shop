package com.ccl.consumer;

import com.ccl.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class ConsumerDemo {
    
    @Service
    @RocketMQMessageListener(topic = Const.topic, selectorExpression = "*", consumerGroup = "group_consumer_all")
    public class ConsumerMsg implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt message) {
            byte[] bytes = message.getBody();
            String msg = new String(bytes);
            log.info("ConsumerMsg接收到消息:" + msg);
        }
    }
    
    @Service
    @RocketMQMessageListener(topic = Const.topic,selectorExpression = Const.tag_shop,consumerGroup = "group_consumer_shop")
    public class ShopConsumerMsg implements RocketMQListener<MessageExt>{
        @Override
        public void onMessage(MessageExt message) {
            byte[] bytes = message.getBody();
            String msg = new String(bytes);
            log.info("ShopConsumerMsg接收到消息:" + msg);
        }
    }
    
    @Service
    @RocketMQMessageListener(topic = Const.topic,selectorExpression = Const.tag_chat,consumerGroup = "group_consumer_chat")
    public class ChatConsumerMsg implements RocketMQListener<MessageExt>{
        @Override
        public void onMessage(MessageExt message) {
            byte[] bytes = message.getBody();
            String msg = new String(bytes);
            log.info("ChatConsumerMsg接收到消息:" + msg);
        }
    }
    
    @Service
    @RocketMQMessageListener(topic = Const.topic,selectorExpression = Const.tag_order,consumerGroup = "group_consumer_order")
    public class OrderConsumerMsg implements RocketMQListener<MessageExt>{
        @Override
        public void onMessage(MessageExt message) {
            byte[] bytes = message.getBody();
            String msg = new String(bytes);
            log.info("OrderConsumerMsg接收到消息:" + msg);
        }
    }
    
}
