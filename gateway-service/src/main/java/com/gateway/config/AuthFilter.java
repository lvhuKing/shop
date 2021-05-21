package com.gateway.config;

import com.common.util.MapToObjectUtil;
import com.gateway.redis.RedisUtils;
import com.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.basic.entity.LoginSuccessDTO;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
* @Description 作用: 全局过滤器，做权限控制
* @Author ccl
* @CreateDate 2020/11/2 17:45
**/
@Configuration
public class AuthFilter implements GlobalFilter, Ordered {
    
    private static final String UN_LOGIN = "请先登录账户";
    private static final String LOGIN_EXPIRED = "账户信息已过期，请重新登录";
    private static final String OTHER_LOGIN = "账户已在其它地方登录";
    
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        /**全局过滤器中，实现重定向*/
//        if("baidu".equals(JwtUtil.getToken(exchange))){
//            /**鉴权失败，重定向其它路径*/
//            String url = "https://www.baidu.com/";
//            ServerHttpResponse response = exchange.getResponse();
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, url);
//            return response.setComplete();
//        }
        /**
         * 不做验证的请求路径
         * 一：判断token是否为空
         * 二：判断token是否过期
         * 三：判断token是否异地登录
         * 四：token验证通过后，判读是否重置token过期时间
         *    更新新token到redis，并将新token放入响应信息
         */
        String requestURL = exchange.getRequest().getPath().value();
        if("/api/basic-service/login".equals(requestURL)){
            return chain.filter(exchange);
        }
        if("/api/basic-service/noticeCode".equals(requestURL)){
            return chain.filter(exchange);
        }
        if("/api/basic-service/registe".equals(requestURL)){
            return chain.filter(exchange);
        }
        String token = JwtUtil.getToken(exchange);
        if(StringUtils.isEmpty(token)){
            return unauthorized(exchange, UN_LOGIN);
        }
        if(JwtUtil.isExpiration(token)){
            return unauthorized(exchange, LOGIN_EXPIRED);
        }
        /**nginx代理*/
        String requestIP = exchange.getRequest().getHeaders().getFirst("X-Forwarded-For");
        if(requestIP == null) requestIP = exchange.getRequest().getHeaders().getFirst("X-Real-IP");
        if(requestIP == null) requestIP = exchange.getRequest().getRemoteAddress().getHostString();
        if(requestIP == null) throw new RuntimeException("无法获取请求客户端IP");
        requestIP = "0:0:0:0:0:0:0:1".equals(requestIP) ? "127.0.0.1" : requestIP;
        Long userId = JwtUtil.getUserId(token);
        String phone = JwtUtil.getPhone(token);
        /**从redis中取出登录信息，用作判断是否异地登录*/
        Object redisObject = redisUtils.getObject("basic:login:" + phone);
        LoginSuccessDTO loginDTO = (LoginSuccessDTO) redisObject;
        if(loginDTO == null){
            return unauthorized(exchange, LOGIN_EXPIRED);
        }
        if(userId.equals(loginDTO.getUserId()) && !requestIP.equals(loginDTO.getOnlineIP())){
            return unauthorized(exchange, OTHER_LOGIN);
        }
        if(JwtUtil.refreshToken(token)){
            token = JwtUtil.createToken(loginDTO.getUserId(), loginDTO.getUsername(), loginDTO.getPhone(), loginDTO.getOnlineIP());
            loginDTO.setToken(token);
            redisUtils.addObject("basic:login:" + phone, loginDTO, 60*61L, TimeUnit.SECONDS);
        }
        /**将新token返回客户端*/
        if("/api/basic-service/logout".equals(requestURL)){
            /**清除redis中登录信息*/
            redisUtils.remove("basic:login:" + phone);
        }else{
            /**传递请求中的token到响应信息中*/
            exchange.getResponse().getHeaders().set(JwtUtil.TOKEN_KEY, token);
        }
        
        return chain.filter(exchange);
    }
    
    /**
     * ，返回统一报文
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange, String errorMsg){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type","application/json;charset=utf-8");
        String s = "{\"status\":\"401\",\"errorMsg\":\""+errorMsg+"\"}";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
    
    

    @Override
    public int getOrder() {
        return -1;
    }
}