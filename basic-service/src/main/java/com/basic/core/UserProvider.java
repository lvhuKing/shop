package com.basic.core;

import com.basic.config.RedisUtils;
import com.basic.repository.TUserService;
import com.basic.util.TencentMessageUtil;
import com.common.model.Const;
import com.common.model.JsonResult;
import com.common.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import shop.basic.UserAPI;
import shop.basic.entity.*;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
public class UserProvider implements UserAPI {
    
    @Resource
    private TUserService tUserServiceImpl;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String senderMailAddress;
    @Value("${tencent.sdkAppID}")
    private String SDKAppID;
    @Value("${tencent.secretId}")
    private String secretId;
    @Value("${tencent.secretKey}")
    private String secretKey;
    @Value("${tencent.sign}")
    private String sign;
    @Value("${tencent.templateID}")
    private String templateID;
    
    /**
     {
     "phone": "15682059298",
     "email": "2755597652@qq.com",
     "oper": "REGISTE"
     }
     */
    @Override
    public JsonResult noticeCode(@Valid NoticeCodeDTORequest dto) {
        if(!Objects.isNull(tUserServiceImpl.selectByPhone(dto.getPhone()))){
            if(Const.RegisteOper.REGISTE.equals(dto.getOper())){
                return new JsonResult(Const.Context.ERROR, Const.Error.EXIST_USER,"");
            }
        }else{
            if(Const.RegisteOper.RESET.equals(dto.getOper())){
                return new JsonResult(Const.Context.ERROR, Const.Error.NULL_USER,"");
            }
        }
        /**生成验证码，放入redis并设置过期时间*/
        String captcha = RandomUtils.randomString(6);
        redisUtils.addObject("captcha:"+dto.getPhone(),captcha,180L, TimeUnit.SECONDS);
        /**发送验证码到手机、邮箱*/
        try {
            /**1、邮件*/
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            /**发件人地址*/
            messageHelper.setFrom(senderMailAddress);
            /**收件人地址*/
            messageHelper.setTo(dto.getEmail());
            messageHelper.setSubject(Const.Email.REGISTER_TITLE);
            /**邮件信息*/
            Map<String, Object> params = new HashMap<>();
            params.put("name", dto.getPhone());
            params.put("code", captcha);
            /**使用thymeleaf作为模板信息*/
            Context context = new Context(LocaleContextHolder.getLocale());
            context.setVariables(params);
            String html = templateEngine.process("sendEmail", context);
            messageHelper.setText(html, true);
            /**发送邮件*/
            javaMailSender.send(message);
            /**2、短信*/
            Map<String, Object> textParams = new HashMap<>();
            textParams.put(Const.TextMessage.PhoneNumbers, dto.getPhone());
            textParams.put(Const.TextMessage.Captcha, captcha);
            textParams.put(Const.TextMessage.SDKAppID, SDKAppID);
            textParams.put(Const.TextMessage.SecretId, secretId);
            textParams.put(Const.TextMessage.SecretKey, secretKey);
            textParams.put(Const.TextMessage.Sign, sign);
            textParams.put(Const.TextMessage.TemplateID, templateID);
            TencentMessageUtil.sendMessage(textParams);
        } catch (Exception e) {
            return new JsonResult(Const.Context.ERROR, Const.Error.NOT_SEND, null);
        }
        
        return null;
    }

    /**
     {
     "phone": "15682059298",
     "username": "HELLO 邮件",
     "password": "123",
     "confirmPassword": "123",
     "email": "2755597652@qq.com",
     "verificationCode": ""
     } 
     */
    @Override
    public JsonResult registe(RegisteDTORequest dto) {
        String captcha = redisUtils.getObject("captcha:" + dto.getPhone()).toString();
        if(captcha == null){
            return new JsonResult(Const.Context.ERROR, Const.Error.EXPIRE_CODE, null);
        }
        if(!captcha.equals(dto.getVerificationCode())){
            return new JsonResult(Const.Context.ERROR, Const.Error.NOT_CODE, null);
        }
        TUser user = new TUser(IdWorker.getId(),dto.getUsername(),dto.getPhone(),dto.getPassword(),dto.getEmail(),"");
        tUserServiceImpl.insert(user);
        return new JsonResult("注册成功");
    }

    @Override
    public JsonResult login(LoginDTORequest dto) {
        /************************账号、密码验证*******************************/
        if(dto.getPhone() == null) return new JsonResult(Const.Context.ERROR,"账号不可为空","");
        if(dto.getPassword() == null) return new JsonResult(Const.Context.ERROR,"密码不可为空","");
        TUser tUser = tUserServiceImpl.selectByPhone(dto.getPhone());
        if(tUser == null) return new JsonResult(Const.Context.ERROR,"账户不存在，请先注册","");
        String encodePwd = Md5SaltUtil.getMD5(dto.getPassword());
        if(!encodePwd.equals(tUser.getPassword())) return new JsonResult(Const.Context.ERROR,"密码错误","");
        
        /********************生成token，并将token令牌返回客户端,有两种方法**********************/
        String requestIP = HttpUtil.getIP();
        if("0:0:0:0:0:0:0:1".equals(requestIP)){
            requestIP = "127.0.0.1";
        }
        String token = JwtUtil.createToken(tUser.getId(), tUser.getUserName(), tUser.getPhone(), requestIP);
        /**(1)令牌放入请求头文件*/
        HttpUtil.getHttpServletResponse().setHeader(JwtUtil.TOKEN_KEY, token);
        /**(2)令牌也可以放入cookie中*/
        Cookie cookie = new Cookie(JwtUtil.TOKEN_KEY, token);
        /**
         * 正常的cookie只能在一个应用中共享，即cookie只能由创建它的应用获得
         * path默认是产生cookie的应用的路径，而设置path的值，就可以保证在同一服务器下所有服务，共享这个cookie
         */
        cookie.setPath("/");
        HttpUtil.getHttpServletResponse().addCookie(cookie);
        /*************************保存登录者信息到redis中*********************************/
        LoginSuccessDTO loginSuccessDTO = LoginSuccessDTO.builder().userId(tUser.getId()).username(tUser.getUserName())
                .phone(tUser.getPhone()).onlineIP(requestIP).loginTime(new Date()).token(token).build();
        redisUtils.addObject(Const.Basic.LOGIN_PREFIX+tUser.getPhone(), loginSuccessDTO, 60*61L, TimeUnit.SECONDS);
        /*********将用户信息返回，用于界面回显**********/
        Map<String,Object> map = new HashMap<>();
        map.put("userId", tUser.getId());
        map.put("userName", tUser.getUserName());
        map.put("phone", tUser.getPhone());
        return new JsonResult(map);
    }
    
    @Override
    public JsonResult logout() {
        HttpUtil.getHttpServletResponse().setHeader(JwtUtil.TOKEN_KEY, "no_token");
        return new JsonResult("退出成功");
    }
    
//    @Override
////    @Cacheable(value = "user-key")
//    public JsonResult logout() {
//        LoginSuccessDTO loginSuccessDTO1 = LoginSuccessDTO.builder().userId(411111111112L).username("测试用户")
//                .phone("15682059298").loginTime(new Date()).build();
//        LoginSuccessDTO loginSuccessDTO2 = LoginSuccessDTO.builder().userId(511111111112L).username("测试用户")
//                .phone("15682059298").loginTime(new Date()).build();
//        LoginSuccessDTO loginSuccessDTO3 = LoginSuccessDTO.builder().userId(611111111112L).username("测试用户")
//                .phone("15682059298").loginTime(new Date()).build();
//        List<LoginSuccessDTO> list = new ArrayList<>();
//        list.add(loginSuccessDTO1);
//        list.add(loginSuccessDTO2);
//        list.add(loginSuccessDTO3);
//        Map<String,Object> map = new HashMap<>();
//        map.put("userId", 411111111112L);
//        map.put("userName", "测试用户");
//        map.put("phone", "15682059298");
//        redisUtils.addRightList("myList",list, 60*60L, TimeUnit.SECONDS);
//        redisUtils.addMap("myMap", map);
//        List myList = redisUtils.getList("myList");
//        Map myMap = redisUtils.getMap("myMap");
//        System.out.println(myList);
//        System.out.println("*********");
//        System.out.println(myMap);
//        return new JsonResult("操作成功");
//    }
}
