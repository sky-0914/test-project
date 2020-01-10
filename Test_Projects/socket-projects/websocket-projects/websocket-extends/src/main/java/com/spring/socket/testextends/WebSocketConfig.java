package com.spring.socket.testextends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: zc
 * @Date: 2019-06-16 17:07
 * @Description: 已经有了消息处理器类，我们必须要对其进行配置，这样Spring才能将消息转发给它。在Spring的Java配置中，这需要在一个配置类上使用@EnableWebSocket，并实现WebSocketConfigurer接口，
 * 如下面的程序清单所示。
 * 程序清单18.2　在Java配置中，启用WebSocket并映射消息处理器
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer, WebMvcConfigurer {
    @Autowired
    private WebSocketController webSocketController;

//    @Bean
//    HandShakeInterceptor sessionInterceptor() {//解决拦截器无法注入问题
//        return new HandShakeInterceptor();
//    }

    /**
     * 即便浏览器和应用服务器的版本都符合要求，两端都支持WebSocket，在这两者之间还有可能出现问题。防火墙代理通常会限制所有除HTTP以外的流量。它们有可能不支持或者（还）没有配置允许进行WebSocket通信。
     * 幸好，提到WebSocket的备用方案，这恰是SockJS所擅长的。SockJS让我们能够使用统一的编程模型，就好像在各个层面都完整支持WebSocket一样，SockJS在底层会提供备用方案。
     * 例如，为了在服务端启用SockJS通信，我们在Spring配置中可以很简单地要求添加该功能。
     *
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketController, "/testWebSocket")
                .addInterceptors(new HandShakeInterceptor()).setAllowedOrigins("*").withSockJS();
    }

}