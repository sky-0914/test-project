package com.spring.cloud.transaction;

import com.yiqiniu.easytrans.EnableEasyTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 赵小超
 */
@EnableEasyTransaction
@EnableTransactionManagement
@EnableEurekaClient
@SpringBootApplication
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}