package com.spring.socket.annotayions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zc
 * @Date: 2019-06-16 11:50
 * @Description: @Component//默认单例
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketController {


    public static Map<String, Session> SESSION_MAP = new ConcurrentHashMap<>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param token
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        log.info("{}建立连接", token);
        SESSION_MAP.put(token, session);
        log.info("建立连接后session数量={}", SESSION_MAP.size());
        this.send(token, "Hello, connection opened!");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("接收到消息：{}", message);
        for (Map.Entry<String, Session> entry : SESSION_MAP.entrySet()) {
            log.info("session.id={}", session.getId());
            if (session.getId().equals(entry.getValue().getId())) {
                log.info("{}发来消息", entry.getKey());
                JSONObject m = JSON.parseObject(message);//将json文本转化为jsonobject
                String toUserId = m.getString("toUserId");
                if (!StringUtils.isEmpty(toUserId)) {
                    Session toUserSession = SESSION_MAP.get(toUserId);
                    if (toUserSession != null) {
                        toUserSession.getBasicRemote().sendText(entry.getKey() + "---->>>" + toUserId + ",msg:" + m.getString("contentText"));
                    }
                }
                break;
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        log.info("有连接关闭");
        Map<String, Session> map = new HashMap<>();
        SESSION_MAP.forEach((k, v) -> {
            if (!session.getId().equals(v.getId())) {
                map.put(k, v);
            }
        });
        SESSION_MAP = map;
        log.info("断开连接后session数量={}", SESSION_MAP.size());
    }

    void send(String token, String message) {
        Session session = SESSION_MAP.get(token);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("发送信息错误{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

}