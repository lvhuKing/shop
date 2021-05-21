package com.ccl.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rocket")
public class TestSend {
    
    @Resource
    private ProducerDemo producerDemo;
    
    @GetMapping("/send")
    public void send(String msg){
        producerDemo.send("这是一个普通消息:" + msg);
        producerDemo.syncSendMsg("这是一个同步消息:" + msg);
//        producerDemo.asyncSendMsg("这是一个异步消息:" + msg);
//        producerDemo.delaySyncSend("这是一个延迟消息:" + msg);
//        producerDemo.sendOneWay("这是一个单向消息:" + msg);
    }
    
}
