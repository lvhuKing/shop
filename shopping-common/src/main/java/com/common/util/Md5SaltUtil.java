package com.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
* @Description 作用: MD5目前只能加密，常用做来登录密码验证
* @Author ccl
* @CreateDate 2020/11/10 10:47
**/
public class Md5SaltUtil {
    /**加密类型*/
    private static String algorithmName = "MD5";
    /**加密次数*/
    private static Integer hashIterations = 1024;
    /**盐值*/
    private static String salt = "123456789";
    
    public static String getMD5(String pwd){
        return String.valueOf(new SimpleHash(algorithmName,pwd,salt,hashIterations));
    }
    
}
