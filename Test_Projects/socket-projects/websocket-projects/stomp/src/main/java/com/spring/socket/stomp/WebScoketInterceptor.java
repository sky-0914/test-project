package com.spring.socket.stomp;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

/**
 * @Author: zc
 * @Date: 2019-06-17 13:59
 * @Description:
 */
@Slf4j
public class WebScoketInterceptor {
    public static class SecurityInterceptor implements ChannelInterceptor, Serializable {
        private static final long serialVersionUID = -8739844836134539154L;

        @Override
        public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
            StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
            // ignore non-STOMP messages like heartbeat messages
            if (sha.getCommand() == null) {
                return;
            }
            String sessionId = sha.getSessionId();
            switch (sha.getCommand()) {
                case CONNECT:
                    log.info("STOMP Connect [sessionId: " + sessionId + "]");
                    break;
                case CONNECTED:
                    log.info("STOMP Connected [sessionId: " + sessionId + "]");
                    break;
                case DISCONNECT:
                    log.info("STOMP Disconnect [sessionId: " + sessionId + "]");
                    break;
                default:
                    break;
            }
        }

        /**
         * 1、设置拦截器
         * 2、首次连接的时候，获取其Header信息，利用Header里面的信息进行权限认证
         * 3、通过认证的用户，使用 accessor.setUser(user); 方法，将登陆信息绑定在该 StompHeaderAccessor 上，在Controller方法上可以获取 StompHeaderAccessor 的相关信息
         *
         * @param message
         * @param channel
         * @return
         */
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            //1、判断是否首次连接
            if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                //2、判断用户名和密码
//                String username = accessor.getNativeHeader("username").get(0);
//                String password = accessor.getNativeHeader("password").get(0);
//                if ("admin".equals(username) && "admin".equals(password)) {
//                    Principal principal = () -> username;
//                    accessor.setUser(principal);
//                    return message;
//                } else {
//                    return null;
//                }
                List<String> nativeHeader = accessor.getNativeHeader("user-token");
                String username = nativeHeader.get(0);
                if (StringUtils.isEmpty(username)) {
                    return null;
                }
                log.info("新用户登陆：{}", username);
                Principal principal = new Principal() {
                    @Override
                    public String getName() {
                        return username;
                    }
                };
                accessor.setUser(principal);
                return message;
            }
            //不是首次连接，已经登陆成功
            return message;
        }
    }
}