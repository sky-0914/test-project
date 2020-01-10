package com.spring.cloud.apollo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:50
 * @Description: 注入到Bean之后，默认是不会自动刷新的,想要实现自动刷新用SpringCloud的刷新注解@RefreshScope
 */
@Data
@Component
@ConfigurationProperties(prefix = "apollo")
@RefreshScope
public class ApolloBeanConfig {
    private String aaa;
    private String bbb;
    private String ccc;
    private String ddd;
}

