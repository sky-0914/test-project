package com.spring.cloud.apollo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:47
 * @Description: 这种方式的自动刷新，当Apollo那边有更新的时候，这边同步刷新
 */
@Data
@Configuration
//@EnableApolloConfig({"dev"})//取哪个命名空间下的
//@EnableApolloConfig//默认不写取得是Applicayion的
public class ApolloConfig {
    @Value("${aaa:111}")
    private String aaa;
    @Value("${bbb:111}")
    private String bbb;
    @Value("${ccc:111}")
    private String ccc;
    @Value("${ddd:111}")
    private String ddd;
    @Value("${env:111}")
    private String env;
    @Value("${spring.application.name:111}")
    private String appName;
}
