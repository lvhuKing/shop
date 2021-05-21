package com.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* jwt允许用户和服务器之间传递安全可靠的信息，jwt实际上是一个字符串，
* 分为三部分：头部（header）、载荷（playload）、签证（signature）
* 具体构成：
* 头部：使用HS256对称签名的算法，令牌类型
* 数据体/载荷：sub-令牌主体，一般设为资源拥有者的唯一标识；
*            exp-令牌的过期时间戳
*            iat-令牌颁发的时间戳
* 签名：加密，对信息的签名结果           
* @Description 作用: JWT工具类
* @Author ccl
* @CreateDate 2020/11/3 14:57
**/
@SuppressWarnings("all")
public class JwtUtil {
    
    /**秘钥*/
    public static String SECRET_KEY = "95bbc40cb7bbf40ebf7b825922894718";
    /**签发者*/
    public static String ISSUER = "ccl";
    /**有效时间*/
    public static Long EXPIRATION = 60 * 60 * 1000L;
    /**前端存放token的key*/
    public static String TOKEN_KEY = "authToken";
    /**token前缀*/
    public static String TOKEN_PREFIX = "Bearer_";

    /**获取token信息*/
    public static String getToken(ServerWebExchange exchange){
        ServerHttpRequest request = exchange.getRequest();
        /**获取头文件中的令牌信息*/
        String token = request.getHeaders().getFirst(TOKEN_KEY);
        /**如果头文件中没有令牌信息，则从请求参数中获取*/
        if(StringUtils.isEmpty(token)){
            request.getQueryParams().getFirst(TOKEN_KEY);
        }
        /**如果请求参数中没有令牌信息，则从cookie中获取*/
        if(StringUtils.isEmpty(token)){
            HttpCookie httpCookie = request.getCookies().getFirst(TOKEN_KEY);
            if(httpCookie != null){
                token = httpCookie.getValue();
            }
        }
        return StringUtils.isEmpty(token) ? null : token;
    }
    
    /** 用户登录成功后,生成JWT*/
    public static String createToken(Long userId,String userName,String phone,String ip){
        /**根据业务添加验证信息*/
        Map<String,Object> claim = new HashMap<>();
        claim.put("userId", userId);
        claim.put("userName", userName);
        /**令牌颁发的时间戳、令牌的过期时间戳*/
        Long nowMills = System.currentTimeMillis();
        Date issueTime = new Date(nowMills);
        Date expirationTime = new Date(nowMills + EXPIRATION);

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claim)
                .setIssuer(ISSUER)
                .setSubject(phone)
                .setIssuedAt(issueTime)
                .setExpiration(expirationTime)
                .compact();
        return TOKEN_PREFIX+token;
    }
    
    /**
     * 获取业务交互信息：token主体
     * token.substring(7) 去掉前缀才是真实的token
     */
    public static Claims getClaims(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.substring(7)).getBody();
        return claims;
    }
    
    /** Claims中直接获取subject */
    public static String getPhone(String token){
        return getClaims(token).getSubject();
    }
    /** Claims中直接获取userId等信息*/
    public static Long getUserId(String token){
        return (Long)getClaims(token).get("userId");
    }
    public static String getUserName(String token){
        return (String)getClaims(token).get("userName");
    }
    /**判断token是否过期: true-已过期*/
    public static Boolean isExpiration(String token){
        return getClaims(token).getExpiration().before(new Date());
    }


//
//    // 获取角色信息
//    public static List<Long> getRole(String token) {
//        return (List<Long>) getTokenBody(token).get("roles");
//    }
//
//    // 获取用户id
//    public static Long getUserId(String token) {
//        return (Long) getTokenBody(token).get("userId");
//    }
//
//    // 获取用户名
//    public static String getUserName(String token) {
//        return (String) getTokenBody(token).get("userName");
//    }
//
//    // 是否已过期
//    public static boolean isExpiration(String token) {
//        return getTokenBody(token).getExpiration().before(new Date());
//    }
//
//    //是否刷新token
//    public static boolean refreshToken(String token) {
//        long expireTime = getTokenBody(token).getExpiration().getTime();
//        long currentTime = System.currentTimeMillis();
//        long time = expireTime - currentTime;
//        if (time < 0) {
//            return false;
//        }
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(expireTime - currentTime);
//        //过期时间小于10分钟时重新生成token
//        return minutes <= 10;
//    }
    
}
