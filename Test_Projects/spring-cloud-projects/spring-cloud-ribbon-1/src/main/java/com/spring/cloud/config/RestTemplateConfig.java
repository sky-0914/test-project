package com.spring.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ZC
 * @time 2018/11/6 16:41
 * @LoadBalanced 整合了Ribbon, 让RestTemplate具备了负载均衡的能力 默认负载均衡的策略是轮训
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        //解决中文乱码
//        template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return template;
    }
}
