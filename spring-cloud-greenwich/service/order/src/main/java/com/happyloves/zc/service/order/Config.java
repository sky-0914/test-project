package com.happyloves.zc.service.order;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author 赵小胖
 * @Date 2019/9/30 23:17
 * @Description: 使用@ComponentScan额外指定待扫描的包，但是不能用在主启动类上，因为这样会覆盖掉默认的包扫描规则，
 * 可以在其他标注了@Configuration的地方配置@ComponentScan(basePackages = { "xxx.yyy"})进行额外指定，这样就能达到效果也不会覆盖默认的包扫描规则，亲试有效。
 */
@Configuration
@ComponentScan(basePackages = {"com.happyloves.zc.service.common"})
public class Config {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
