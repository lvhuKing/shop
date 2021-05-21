package com.product.rocketmq;

import com.common.model.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MqTest {
    
    @Resource
    private RocketMQProducer rocketMQProducer;
    
    @GetMapping("/send")
    public JsonResult send(String msg){
        return new JsonResult(rocketMQProducer.syncSend(msg));
    }
    
    @GetMapping("/send2")
    public JsonResult send2(String msg){
        rocketMQProducer.asyncSend(msg);
        return new JsonResult("消息已发送");
    }
    
    @GetMapping("/send3")
    public JsonResult send3(String msg){
        rocketMQProducer.sendMsg3(msg, 1);
        return new JsonResult("消息已发送");
    }
    
    @GetMapping("/send4")
    public JsonResult send4(String msg){
        rocketMQProducer.sendMsg4(msg);
        return new JsonResult("消息已发送");
    }
    
}
