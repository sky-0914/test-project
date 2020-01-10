package com.spring.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵小超
 * @Date: 2018/11/6 22:58
 * @Description: 自定义Ribbon负载均衡算法
 * RibbonClientConfiguration默认配置类，ribbonRule方法
 * new RandomRule()随机算法
 * 第一种方式-该配置类不能再SpringBoot启动类扫描下配置。得在SpringBoot启动类包外
 * 第二种方式-自定义注解排除该类
 */
@Configuration
public class TestConfiguration {

    @Autowired
    private IClientConfig clientConfig;

    /**
     * 默认忽视Zone的Rule
     * RibbonClientConfiguration
     *
     * @param clientConfig
     * @return
     */
    @Bean
    public IRule ribbonRule(IClientConfig clientConfig) {
        return new RandomRule();
    }
}
