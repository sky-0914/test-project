package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 赵小超
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AlibabaFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaFeignApplication.class, args);
    }
}
