package com.spring.socket.testextends;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: zc
 * @Date: 2019-06-16 17:16
 * @Description: TextWebSocketHandler是AbstractWebSocketHandler的子类，它会拒绝处理二进制消息。它重载了handleBinaryMessage()方法，如果收到二进制消息的时候，将会关闭WebSocket连接。
 * 我们可以扩展TextWebSocketHandler或BinaryWebSocketHandler。
 * 与之类似，BinaryWebSocketHandler也是AbstractWeb-SocketHandler的子类，它重载了handleTextMessage()方法，如果接收到文本消息的话，将会关闭连接。
 */
@Slf4j
@Component
public class WebSocketController extends TextWebSocketHandler {

    private static AtomicInteger onlineCount = new AtomicInteger(0);

    private static final ArrayList<WebSocketSession> sessions = new ArrayList<>();

    /**
     * @OnOpen 连接成功时候，会触发页面上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("connect to the websocket success......当前数量:" + sessions.size());
        sessions.add(session);
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }

    /**
     * @OnClose 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.debug("websocket connection closed......");
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        System.out.println("用户" + username + "已退出！");
        sessions.remove(session);
        System.out.println("剩余在线用户" + sessions.size());
    }

    /**
     * @OnMessage js调用websocket.send时候，会调用该方法
     * 除了重载WebSocketHandler中所定义的五个方法以外，我们还可以重载AbstractWebSocketHandler中所定义的三个方法：
     * handleBinaryMessage()
     * handlePongMessage()
     * handleTextMessage()
     * 这三个方法只是handleMessage()方法的具体化，每个方法对应于某一种特定类型的消息。
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("Receive a message from client: " + message.toString());
    }

    /**
     * @OnError
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.debug("websocket connection closed......");
        sessions.remove(session);
    }

    /**
     * 是否支持消息拆分发送：如果接收的数据量比较大，最好打开(true), 否则可能会导致接收失败。
     * 如果出现WebSocket连接接收一次数据后就自动断开，应检查是否是这里的问题。
     *
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : sessions) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : sessions) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
