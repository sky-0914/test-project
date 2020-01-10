package com.spring.cloud.socket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.stereotype.Component;

import javax.websocket.ClientEndpoint;
import java.net.URI;

/**
 * @Author: zc
 * @Date: 2019-06-16 16:39
 * @Description:
 */
@Slf4j
@Component
@ClientEndpoint
public class TestWebSocketClient extends org.java_websocket.client.WebSocketClient {


    public TestWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public TestWebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("Open a WebSocket connection on client. ");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        log.info("Close a WebSocket connection on client. ");
    }

    @Override
    public void onMessage(String msg) {
        log.info("WebSocketClient receives a message: " + msg);
    }

    @Override
    public void onError(Exception exception) {
        log.error("WebSocketClient exception. ", exception);
    }
}
