package com.spring.cloud.alibaba.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:50
 * @Description: @RefreshScope，主要用来让这个类下的配置内容支持动态刷新，也就是当我们的应用启动之后，修改了Nacos中的配置内容之后，这里也会马上生效。
 */
@Data
@Component
@ConfigurationProperties(prefix = "test")
@RefreshScope
public class BeanConfig {
    private String aaa;
    private String bbb;
    private String ccc;
    private String ddd;
}

