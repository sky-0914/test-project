package com.spring.config;

import feign.Feign;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author: 赵小超
 * @Date: 2018/11/9 00:31
 * @Description:
 */
@Configuration
public class FeignConfig2 {

//    /**
//     * 使用Feign默认配置。
//     * 如果不配置这个使用的是Spring默认注解
//     *
//     * @return
//     */
//    @Bean
//    public Contract feignContract() {
//        return new feign.Contract.Default();
//    }

    /**
     * 配置Feign日志
     * 您可以为每个客户端配置的Logger.Level对象告诉Feign记录多少。选择是：
     * NONE，无记录（DEFAULT）。
     * BASIC，只记录请求方法和URL以及响应状态代码和执行时间。
     * HEADERS，记录基本信息以及请求和响应标头。
     * FULL，记录请求和响应的头文件，正文和元数据。
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 禁用单个Feign客户端的Hystrix
     *
     * @return
     */
    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
