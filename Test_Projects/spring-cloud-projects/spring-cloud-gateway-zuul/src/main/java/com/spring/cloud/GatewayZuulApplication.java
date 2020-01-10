package com.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author 赵小超
 * @EnableZuulProxy 配置路由器
 * <p>
 * Zuul使用的默认HTTP客户端现在由Apache HTTP Client而不是已弃用的功能区支持RestClient。
 * 分别使用RestClient或okhttp3.OkHttpClient设置ribbon.restclient.enabled=true或ribbon.okhttp.enabled=true。
 * 如果要自定义Apache HTTP客户端或OK HTTP客户端，请提供类型为ClosableHttpClient或的bean OkHttpClient。
 */
@EnableZuulProxy
@SpringBootApplication
public class GatewayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayZuulApplication.class, args);
    }

//    /**
//     * 正则表达式路由代理
//     * 微服务的ServiceId必须加上{版本号：v1.1}
//     * server-1-v1.1 -> v1.1/server-1
//     * <p>
//     * 配置了这个Bean yml 不需要任何配置
//     * 如果没有配置这个Bean，那么就会按照默认的
//     *
//     * @return
//     */
//    @Bean
//    public PatternServiceRouteMapper serviceRouteMapper() {
//        return new PatternServiceRouteMapper(
//                "(?<name>^.+)-(?<version>v.+$)",
//                "${version}/${name}");
//    }
}

