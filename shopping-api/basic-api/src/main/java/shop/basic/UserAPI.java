package shop.basic;

import com.common.model.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.basic.entity.LoginDTORequest;
import shop.basic.entity.NoticeCodeDTORequest;
import shop.basic.entity.RegisteDTORequest;
import shop.basic.fallback.UserAPIFallback;

import javax.validation.Valid;

@FeignClient(value = "basic-service", fallback = UserAPIFallback.class, configuration = UserAPIFallback.class)
public interface UserAPI {
    
    @PostMapping("/noticeCode")
    JsonResult noticeCode(@RequestBody @Valid NoticeCodeDTORequest dto);
    
    /**
     * （1）生成验证码、短信/邮箱验证、判断是否过期/是否匹配、注册成功
     * （2）注册成功后，发送消息到rocketmq，通过邮件/短信通知用户注册成功 
     */
    @PostMapping("/registe")
    JsonResult registe(@RequestBody RegisteDTORequest dto);
    
    @PostMapping("/login")
    JsonResult login(@RequestBody LoginDTORequest dto);
    
    @GetMapping("/logout")
    JsonResult logout();
    
}
