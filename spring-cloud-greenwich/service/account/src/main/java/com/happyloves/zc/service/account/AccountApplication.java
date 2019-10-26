package com.happyloves.zc.service.account;

import com.happyloves.zc.service.account.config.GoodsFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


//@EnableFeignClients(defaultConfiguration = GoodsFeignConfig.class)//全局配置-代码实现
@EnableFeignClients//全局配置-配置属性实现
//@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
