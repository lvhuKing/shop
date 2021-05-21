package tx.rocketmq.consumer3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Consumer3Application {
    public static void main(String[] args) {
        SpringApplication.run(Consumer3Application.class, args);
    }
}
