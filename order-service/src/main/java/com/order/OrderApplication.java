package com.order;

//import com.order.config.DataSourceProxyAutoConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

//import org.springframework.context.annotation.Import;

@SpringBootApplication
// (exclude = {DataSourceAutoConfiguration.class})
//@Import({DataSourceProxyAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"shop.account","shop.product"})
@MapperScan("com.order.mapper")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
