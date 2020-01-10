package com.spring.cloud.socket;

import org.java_websocket.client.WebSocketClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Test
    public void contextLoads() throws URISyntaxException {
        String serverUrl = "ws://127.0.0.1:8080/testWebsocket";
        URI recognizeUri = new URI(serverUrl);
        WebSocketClient client = new TestWebSocketClient(recognizeUri);
        client.connect();
        client.send("This is a message from client. ");
    }

}
