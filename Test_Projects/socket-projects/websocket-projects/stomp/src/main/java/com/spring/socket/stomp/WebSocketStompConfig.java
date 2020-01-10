package com.spring.socket.stomp;

import com.spring.socket.stomp.WebScoketInterceptor.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Author: zc
 * @Date: 2019-06-16 18:32
 * @Description: 在Spring MVC中为控制器方法添加@MessageMapping注解，使其处理STOMP消息，它与带有@RequestMapping注解的方法处理HTTP请求的方式非常类似。
 * 但是与@RequestMapping不同的是 @MessageMapping的功能无法通过@EnableWebMvc启用，而是@EnableWebSocketMessageBroker。
 * Spring的Web消息功能基于消息代理（message broker）构建，因此除了告诉Spring我们想要处理消息以外，还有其他的内容需要配置。
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //它重载了registerStompEndpoints()方法，将“/marcopolo”注册为STOMP端点。这个路径与之前发送和接收消息的目的地路径有所不同。这是一个端点，客户端在订阅或发布消息到目的地路径前，要连接该端点。
        // setAllowedOrigins("*")跨域，withSockJS()的作用是开启SockJS支持
        registry.addEndpoint("/marcopolo", "/ws-stomp").setAllowedOrigins("*").withSockJS();
//                .setInterceptors(new HandshakeInterceptor() {
//                    @Override
//                    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
//                        //拿到session，自己完成验证代码
//                        HttpSession session = getSession(serverHttpRequest);
//                        return true;
//                    }
//
//                    @Override
//                    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
//
//                    }
//
//                    private HttpSession getSession(ServerHttpRequest request) {
//                        if (request instanceof ServletServerHttpRequest) {
//                            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
//                            return serverRequest.getServletRequest().getSession();
//                        }
//                        return null;
//                    }
//                });//权限拦截器
    }

    /**
     * 使用STOMP内存代理
     * WebSocketStompConfig还通过重载configureMessageBroker()方法配置了一个简单的消息代理。
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //消息代理将会处理前缀为“/topic”和“/queue”的消息。除此之外。
        // 客户端订阅地址的前缀信息, 也就是客户端接收服务端消息的地址的前缀信息
        // 定义了客户端接收的地址前缀
        registry.enableSimpleBroker("/queue", "/topic", "/receive");
        //发往应用程序的消息将会带有“/app”前缀。
        // 指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
        // 定义了客户端发送的地址前缀
        registry.setApplicationDestinationPrefixes("/app", "/send");
    }

//    /**
//     * 启用STOMP代理中继
//     * 于生产环境下的应用来说，你可能会希望使用真正支持STOMP的代理来支撑WebSocket消息，如RabbitMQ或ActiveMQ。
//     * 这样的代理提供了可扩展性和健壮性更好的消息功能，当然它们也会完整支持STOMP命令。
//     * 我们需要根据相关的文档来为STOMP搭建代理。搭建就绪之后，就可以使用STOMP代理来替换内存代理了，只需按照如下方式重载configureMessageBroker()方法即可：
//     * 默认情况下，STOMP代理中继会假设代理监听localhost的61613端口，并且客户端的username和password均为“guest”。
//     * 如果你的STOMP代理位于其他的服务器上，或者配置成了不同的客户端凭证，那么我们可以在启用STOMP代理中继的时候，需要配置这些细节信息：
//     *
//     * @param registry
//     */
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        //configureMessageBroker()方法的第一行代码启用了STOMP代理中继（broker relay）功能，并将其目的地前缀设置为“/topic”和“/queue”。
//        // 这样的话，Spring就能知道所有目的地前缀为“/topic”或“/queue”的消息都会发送到STOMP代理中。
//        registry.enableStompBrokerRelay("/queue", "/topic")
//                .setRelayHost("rabbit.someotherserver")
//                .setRelayPort(62623)
//                .setClientLogin("marcopolo")
//                .setClientPasscode("letmein01");
//        //configureMessageBroker()方法中将应用的前缀设置为“/app”。所有目的地以“/app”打头的消息都将会路由到带有@MessageMapping注解的方法中，而不会发布到代理队列或主题中。
//        registry.setApplicationDestinationPrefixes("/app");
//    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //注册拦截器
        registration.interceptors(securityInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(8);
        registration.interceptors(securityInterceptor());
    }
}
