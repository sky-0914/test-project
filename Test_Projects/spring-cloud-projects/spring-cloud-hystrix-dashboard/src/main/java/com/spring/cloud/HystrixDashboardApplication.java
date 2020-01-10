package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author 赵小超
 * @RibbonClient(name = "server-1", configuration = TestConfiguration.class)指定服务ID Service-ID 走我们自定义的Ribbon负载均衡算法
 */
@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard//断路器Hystrix仪表盘
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}



