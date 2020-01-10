package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 赵小超
 */
@SpringBootApplication
@EnableEurekaServer
public class Eureka3Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka3Application.class, args);
    }
}
