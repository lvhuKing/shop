package tx.rocketmq.producer.core;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @Description 作用：生产者，发送不同类型的消息进行测试
* @Author ccl
* @CreateDate 2021/3/23 17:00
**/
@RestController
@RequestMapping("/mq")
public class ProducerController {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.sync-tag}")
    private String syncTag;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.async-tag}")
    private String asynTag;

    @Value(value = "${rocketmq.producer.topic}:${rocketmq.producer.oneway-tag}")
    private String onewayTag;

    /**1、普通消息*/
    @GetMapping("/send")
    public void sendFirst(String msg) {
        Message message = MessageBuilder.withPayload("第一个消息："+ msg).build();
        rocketMQTemplate.send("tx-topic:test",message);
    }
    /**2、同步消息*/
    @GetMapping("/syncSend")
    public void syncSend(String msg) {
        // 构建消息
        Message<String> message = MessageBuilder.withPayload(msg).setHeader(RocketMQHeaders.KEYS, msg).build();
        // 设置发送地和消息信息并发送同步消息
        SendResult sendResult = rocketMQTemplate.syncSend(syncTag, message);
        System.out.println("【syncSend】发送结果："+sendResult.toString());
    }
    /**3、异步消息*/
    @GetMapping("/asyncSend")
    public void asyncSend(String msg){
        // 构建消息
        Message<String> message = MessageBuilder.withPayload(msg).setHeader(RocketMQHeaders.KEYS, msg).build();
        // 设置发送地和消息信息并发送异步消息
        rocketMQTemplate.asyncSend(asynTag, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("【asyncSend】发送成功");
            }
            @Override
            public void onException(Throwable throwable) {
                System.out.println("【asyncSend】发送失败");
            }
        });
    }
    /**4、单向消息*/
    @GetMapping("/sendOneWay")
    public void sendOneWay(String msg){
        Message<String> message = MessageBuilder.withPayload(msg).setHeader(RocketMQHeaders.KEYS, msg).build();
        rocketMQTemplate.sendOneWay(onewayTag, message);
        System.out.println("【sendOneWay】已发送");
    }
    /**5、包含顺序的单向消息*/
    @GetMapping("/orderlyMessageQueue")
    public void orderlyMessageQueue(){
        for(int i=0; i<10; i++){
            String orderId = String.valueOf(i);
            String step = "步骤"+i;
            String sendInfo = "orderId:"+orderId+" step:"+step;
            rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, org.apache.rocketmq.common.message.Message message, Object o) {
                    System.out.println("########MessageQueueSelector接口参数#######");
                    System.out.println("list:"+list.toString());
                    System.out.println("message:"+message);
                    System.out.println("o:"+o.toString());
                    int queueNum = Integer.valueOf(String.valueOf(o)) % list.size();
                    System.out.println(queueNum);
                    System.out.println(list.get(queueNum));
                    return list.get(queueNum);
                }
            });
            Message<String> message = MessageBuilder.withPayload(sendInfo).setHeader(RocketMQHeaders.KEYS, orderId).build();
            // 设置发送地和消息信息并发送消息（Orderly） syncSendOrderly(String destination, Object payload, String hashKey)
            rocketMQTemplate.syncSendOrderly(syncTag, sendInfo, orderId);
        }
        // message 要发的那条消息
        // queue 选择器，向topic中的哪个queue去写消息
        // 手动选择一个queue
        // mqs  当前topic里面包含的所有queue
        // msg  具体要发的那条消息
        // arg  对应到send()里的args, 也就是2000前面的那个0
        // 向固定的一个queue里写消息，比如这里就是向第一个queue里写消息
//        Message message = new Message("orderTopic", ("hello!" + i).getBytes());
//        for(int i=0; i<10; i++) {
//            producer.send(message,
//                        new MessageQueueSelector() {
//                            @Override
//                            public MessageQueue select(List mqs, Message msg, Object arg) {
//                                if (Integer.parseInt(arg.toString()) % 2 == 0) {
//                                    return mqs.get(0);
//                                } else {
//                                    return mqs.get(1);
//                                }
//                            }
//                        },
//                    i, 2000);
//        }
    }
    /**6、延迟消息*/
    @GetMapping("/delaySyncSend")
    public void pushDelayMessage(String msg) {
        // 构建消息
        Message<String> message = MessageBuilder.withPayload(msg)
                .setHeader(RocketMQHeaders.KEYS, msg)
                .build();
        // 设置超时和延时推送
        // 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
        // private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        SendResult sendResult = rocketMQTemplate.syncSend(syncTag, message, 1000L, 4);
    }
    
    /**7、sql过滤消息*/
    @GetMapping("/sqlMessage")
    public void sqlMessage(String msg) {
        // 创建消息集合
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String code = "00" + i;
            String eachMsg = msg + i;
            Message<String> message = MessageBuilder.withPayload(eachMsg)
                    .setHeader(RocketMQHeaders.KEYS, code)
                    .setHeader("money", i)
                    .build();
            messages.add(message);
        }
        rocketMQTemplate.syncSend(syncTag, messages);
    }
    
    /**8、事务消息*/
    @GetMapping("/transactionMessage")
    public void transactionMessage(Long orderId) {
        // 创建消息
        String messageStr = "order id : " + orderId;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, orderId)
                .setHeader("money", 10)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, orderId)
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(syncTag, message, null);
        System.out.println("事务消息结果："+transactionSendResult);
    }
    
}
