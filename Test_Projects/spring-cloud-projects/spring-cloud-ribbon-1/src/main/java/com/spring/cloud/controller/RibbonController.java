package com.spring.cloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZC
 * @time 2018/11/6 16:38
 */
@RestController
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @HystrixCommand(fallbackMethod = "testFallback")
    @GetMapping("/test")
    public Object test(String id) {
        // server-1 === VIP virtual IP 虚拟IP （请求服务提供者的ServiceID）
        return this.restTemplate.getForObject("http://server-1/server?id=" + id, String.class);
    }

    public Object testFallback(String id) {
        return "Ribbon-1-testFallback";
    }

    @GetMapping("/testRule")
    public String testRule() {
        ServiceInstance serviceInstance1 = loadBalancerClient.choose("server-1");
        System.out.println("111=== " + serviceInstance1.getServiceId() + ":" + serviceInstance1.getHost() + ":" + serviceInstance1.getPort());
        ServiceInstance serviceInstance2 = loadBalancerClient.choose("server-2");
        System.out.println("111=== " + serviceInstance2.getServiceId() + ":" + serviceInstance2.getHost() + ":" + serviceInstance2.getPort());
        return "testRule";
    }
}
