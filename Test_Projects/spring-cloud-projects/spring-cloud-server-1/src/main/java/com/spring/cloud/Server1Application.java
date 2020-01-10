package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 赵小超
 * @EnableEurekaClient 只能Eureka可用
 * @EnableDiscoveryClient 可用其他服务注册
 */
@EnableEurekaClient
//@EnableDiscoveryClient
@SpringBootApplication
public class Server1Application {
//public class Server1Application testextends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Server1Application.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(Server1Application.class, args);
    }
}
