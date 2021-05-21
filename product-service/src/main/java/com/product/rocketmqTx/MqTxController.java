package com.product.rocketmqTx;

import com.alibaba.fastjson.JSON;
import com.product.entity.AbcPerson;
import com.product.entity.TransferDto;
import com.product.mapper.AbcPersonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RestController
public class MqTxController {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private AbcPersonMapper abcPersonMapper;

    @PostMapping("/abcToHx")
    public String abcToHx(String mobile, BigDecimal transferMoney, String tarMobile) {
        AbcPerson abcPerson = abcPersonMapper.getByMobile(mobile);
        if (ObjectUtils.isNotEmpty(abcPerson) && ObjectUtils.isNotEmpty(transferMoney) && abcPerson.getBanlance().doubleValue() > transferMoney.doubleValue()) {
            TransferDto transferDto = new TransferDto();
            transferDto.setMobile(mobile);
            transferDto.setTarMobile(tarMobile);
            transferDto.setMoney(transferMoney);
            transferDto.setUserId(abcPerson.getUserId());
            transferDto.setDistributedId(SnowflakeUtil.getSnowflakeId());
            // 发送半消息
            String destination = "transfer-topic:toHx";
            Message message = MessageBuilder.withPayload(JSON.toJSONString(transferDto)).build();
            TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(destination, message, null);
            log.warn("发送半消息：" + message + ",响应内容：" + result);
            return "SUCCESS";
        }
        return "FAIL";
    }
}
