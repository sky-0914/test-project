package com.spring.cloud.alibaba.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:47
 * @Description: @RefreshScope，主要用来让这个类下的配置内容支持动态刷新，也就是当我们的应用启动之后，修改了Nacos中的配置内容之后，这里也会马上生效。
 */
@Data
@Configuration
@RefreshScope
public class ValueConfig {
    @Value("${test.aaa:111}")
    private String aaa;
    @Value("${test.bbb:111}")
    private String bbb;
    @Value("${test.ccc:111}")
    private String ccc;
    @Value("${test.ddd:111}")
    private String ddd;
}
