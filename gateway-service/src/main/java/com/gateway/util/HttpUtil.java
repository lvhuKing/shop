package com.gateway.util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description 作用: HTTP工具
* @Author ccl
* @CreateDate 2020/11/3 17:41
**/
@SuppressWarnings("all")
public class HttpUtil {
    
    private static ServletRequestAttributes getServletRequestAttributes(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes; 
    }
    
    public static HttpServletRequest getHttpServletRequest(){
        HttpServletRequest httpServletRequest = getServletRequestAttributes().getRequest();
        return httpServletRequest;
    }
    
    public static HttpServletResponse getHttpServletResponse(){
        HttpServletResponse httpServletResponse = getServletRequestAttributes().getResponse();
        return httpServletResponse;
    }
    
    /**
     * 获取客户端地址 
     */
    public static String getIP(){
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if(!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){
            int index = ip.indexOf(",");
            if(index < 0 ){
                return ip;
            }else{
                /**多次反向代理后会出现多个ip,取第一个才是真实ip*/
                return ip.substring(0, index);
            }
        }
        ip = httpServletRequest.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){
            return ip;
        }
        return httpServletRequest.getRemoteAddr();
    }
}
