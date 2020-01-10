package com.spring.socket.annotayions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: zc
 * @Date: 2019-06-16 11:21
 * @Description:如果是你采用springboot内置容器启动项目的，则需要配置一个Bean。如果是采用外部的容器，则可以不需要配置。
 */
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter 作用
     * <p>
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
