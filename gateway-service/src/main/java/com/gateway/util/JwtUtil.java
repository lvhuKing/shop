package com.gateway.util;

import io.jsonwebtoken.*;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
            token = request.getQueryParams().getFirst(TOKEN_KEY);
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
        try {
            return getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
    
    /**是否更新token过期时间: 仅剩10分钟内过期的，重新生成token*/
    public static Boolean refreshToken(String token){
        long expireTime = getClaims(token).getExpiration().getTime();
        long currentTime = System.currentTimeMillis();
        Long milliseconds = expireTime - currentTime;
        if(milliseconds < 0) return false;
        Long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        return minutes <= 10;
    }

}
