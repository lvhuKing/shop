package com.common.model;

/**
* @Description 作用：通用常量
* @Author ccl
* @CreateDate 2020/9/9 9:58
**/
public interface Const {
    String TEST = "test";
    
    /**上下文*/
    interface Context{
        String OK = "ok";
        String ERROR = "error";
        String ERRORMSG = "服务繁忙，请稍后";
        String LIMITMSG = "服务限流，请稍后";
        String FALLBACKMSG = "服务熔断，请稍后";
    }
    
    interface Basic{
        String LOGIN_PREFIX = "basic:login:";
    }
    
    /**信息类型：注册、重置密码*/
    interface RegisteOper{
        String REGISTE = "REGISTE";
        String RESET = "RESET";
    }

    interface Email{
        String FORGET_PASSWORD_TITLE = "密码重置";
        String REGISTER_TITLE = "注册账号";
    }
    
    /**短信参数*/
    interface TextMessage{
        /**手机号、验证码*/
        String PhoneNumbers = "PhoneNumbers";
        String Captcha = "Captcha";
        
        /**tencentyun: APPID、API访问秘钥、短信签名内容、短信模板ID*/
        String SDKAppID = "SDKAppID";
        String SecretId = "SecretId";
        String SecretKey = "SecretKey";
        String Sign = "Sign";
        String TemplateID = "TemplateID";
        
        /**aliyun: AK信息、发送手机号、短信签名、短信模板、验证码**/
        String AccessKeyId = "AccessKeyId";
        String AccessKeySecret = "AccessKeySecret";
        String SignName = "SignName";
        String TemplateCode = "TemplateCode";
    } 
    
    interface Error{
        String EXIST_USER = "账户已被注册";
        String NULL_USER = "账户未被注册";
        String NOT_SEND = "验证码发送失败,请检查后台！";
        String EXPIRE_CODE = "验证码已过期";
        String NOT_CODE = "验证码错误";
    }
}
