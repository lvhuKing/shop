package com.basic;

import com.basic.config.RedisUtils;
import com.common.model.Const;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.basic.mapper")
public class BasicApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicApplication.class, args);
    }
    
    /**启动服务时，完成初始化*/
    @Configuration
    class StartListener implements CommandLineRunner{
        
        @Resource
        private RedisUtils redisUtils;
        
        @Override
        public void run(String... args) throws Exception {
            /**清空redis中登录相关的信息*/
            redisUtils.removeBlear(Const.Basic.LOGIN_PREFIX+"*");
        }
    }
}
