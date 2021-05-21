//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RocketMqTest1 {
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//
//
//    /**
//     *发送可靠同步消息
//     */
//    @Test
//    public void syncSendTest(){
//
//        /**
//         * 发送可靠同步消息 ,可以拿到SendResult 返回数据
//         * 同步发送是指消息发送出去后，会在收到mq发出响应之后才会发送下一个数据包的通讯方式。
//         * 这种方式应用场景非常广泛，例如重要的右键通知、报名短信通知、营销短信等。
//         * 参数1： topic:tag
//         * 参数2:  消息体 可以为一个对象
//         * 参数3： 超时时间 毫秒
//         */
//        // rocketMQTemplate.syncSend("xxx","asdf");
//        rocketMQTemplate.syncSend("test-topic-1:myTag","这是一条rmq消息1");
//        rocketMQTemplate.syncSend("test-topic-1:myTag","这是一条rmq消息2");
//
//        System.out.println("ok");
//    }
//
//}