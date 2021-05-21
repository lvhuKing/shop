package tx.rocketmq.consumer1.core;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Consumer1 {

    /**
    @Service
    @RocketMQMessageListener(topic = "", selectorExpression = "", consumerGroup = "")
    public class Consumer1_ implements RocketMQListener<String>{
        @Override
        public void onMessage(String message) {
            
        }
    }
    **/
    
    @Service
    @RocketMQMessageListener(topic = "tx-topic", selectorExpression = "test", consumerGroup = "group-consumer1-test")
    public class Consumer1_1 implements RocketMQListener<String>{
        @Override
        public void onMessage(String message) {
            System.out.println("消费者一收到消息："+message);
        }
    }
    // 监听多个tag时使用||进行分割，如果监听所有用*或者不填
//    @Service
//    @RocketMQMessageListener(
//            consumerGroup = "group_tx_consumer",
//            topic = "tx-topic",
//            selectorExpression = "test||tx-sync-tags||tx-async-tags||tx-oneway-tags"
//    )
//    public class Consumer1_2 implements RocketMQListener<String>{
//        @Override
//        public void onMessage(String message) {
//            System.out.println("消费所有消息："+message);
//        }
//    }
    /**
     * 过滤类型selectorType: SelectorType.TAG（默认）、SelectorType.SQL92
     * 消息顺序处理模式consumeMode: 
     *  ConsumeMode.CONCURRENTLY（默认所有线程可以同时处理同一队列,不保证信息顺序）
     *  ConsumeMode.ORDERLY 有序执行，FIFO
     * 消息模型：messageModel: MessageModel.CLUSTERING（聚类）、MessageModel.BROADCASTING（广播）
     */ 
//    @Service
//    @RocketMQMessageListener(
//            consumerGroup = "group_tx_consumer1",
//            topic = "tx-topic",
//            selectorExpression = "money > 5",
//            messageModel = MessageModel.CLUSTERING,
//            consumeMode = ConsumeMode.ORDERLY,
//            selectorType = SelectorType.SQL92
//    )
//    public class Consumer1_3 implements RocketMQListener<String>{
//        @Override
//        public void onMessage(String message) {
//            System.out.println("有序消费sql过滤消息："+message);
//        }
//    }
    
    @Service
    @RocketMQMessageListener(
            consumerGroup = "group_tx_consumer2",
            topic = "tx-topic",
            selectorExpression = "tx-sync-tags||tx-async-tags||tx-oneway-tags",
            messageModel = MessageModel.CLUSTERING,
            consumeMode = ConsumeMode.ORDERLY
    )
    public class Consumer1_4 implements RocketMQListener<String>{
        @Override
        public void onMessage(String message) {
            System.out.println("有序消费所有消息："+message);
            try {
                Thread.sleep(10*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
