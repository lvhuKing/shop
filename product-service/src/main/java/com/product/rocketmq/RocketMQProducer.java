package com.product.rocketmq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RocketMQProducer {
    
    public final static String topic1 = "topic_test1";
    public final static String topic2 = "topic_test2";
    public final static String topic3 = "topic_test3";
    public final static String topicTag4 = "topic_test4:tag";
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    
    /**发送普通消息*/
    public String syncSend(String msg){
        SendResult sendResult = rocketMQTemplate.syncSend(topic1, MessageBuilder.withPayload(msg).build());
        return sendResult.toString();
    }

    /**发送异步消息：SendCallback中处理消息成功/失败逻辑*/
    public void asyncSend(String msg){
        rocketMQTemplate.asyncSend(topic2, MessageBuilder.withPayload(msg).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 消息发送成功回调
                System.out.println("消息成功");
            }
            @Override
            public void onException(Throwable throwable) {
                // 消息发送失败回调
                System.out.println("消息失败");
            }
        });
    }
    
    /**延迟发送消息*/
    public void sendMsg3(String msg, Integer delayLevel){
        rocketMQTemplate.syncSend(topic3, MessageBuilder.withPayload(msg).build(), 50000, delayLevel);
    }
    
    /**发送带tag的消息，直接在主题后加上:tag */
    public void sendMsg4(String msg){
        rocketMQTemplate.syncSend(topicTag4, MessageBuilder.withPayload(msg).build());
    }
    
}
