package com.spring.socket.stomp;

import com.alibaba.fastjson.JSON;
import com.spring.socket.stomp.TestVO.TestRequest;
import com.spring.socket.stomp.TestVO.TestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @Author: zc
 * @Date: 2019-06-17 14:15
 * @Description: 使用 @MessageMapping 或者 @SubscribeMapping 注解可以处理客户端发送过来的消息，并选择方法是否有返回值。
 */
@Slf4j
@Controller
public class WSController {

    //SimpMessagingTemplate能够在应用的任何地方发布消息--private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 广播消息，不指定用户，所有订阅此的用户都能收到消息
     *
     * @param request
     */
    @MessageMapping("/message1")
    public void message1(TestRequest request) {
        log.info("Received message1: {}", JSON.toJSONString(request));
        // 使用SimpMessagingTemplate将所有新创建的Spittle以消息的形式发布到“/topic/test”主题上。
        simpMessageSendingOperations.convertAndSend("/receive/test1", request);
    }

    /**
     * 接收消息没有代理
     *
     * @param request
     * @return
     * @MessageMapping 注解的控制器方法有返回值的话，返回值会被发送到消息代理，只不过会添加上"/topic"前缀。可以使用@SendTo 重写消息目的地；
     */
    @MessageMapping("/message2")
    public TestResponse message2(TestRequest request) {
        log.info("Received message2: {}", JSON.toJSONString(request));
        TestResponse response = new TestResponse();
        response.setData(request);
        response.setFlag(true);
        response.setMessage("AAAAA");
        return response;
    }

    /**
     * 接收消息有代理@SendTo
     *
     * @param request
     * @return
     * @MessageMapping 注解标示的方法有返回值的时候，返回的对象将会进行转换（通过消息转换器）并放到STOMP帧的负载中，然后发送给消息代理。
     * 默认情况下，帧所发往的目的地会与触发处理器方法的目的地相同，只不过会添加上“/topic”前缀。就本例而言，这意味着handleShout()方法所返回的Shout对象会写入到STOMP帧的负载中，并发布到“/topic/marco”目的地。
     * 不过，我们可以通过为方法添加@SendTo注解，重载目的地
     */
    @MessageMapping("/message3")
    @SendTo("/receive/test1")
    public TestResponse message3(TestRequest request) {
        log.info("Received message3: {}", JSON.toJSONString(request));
        TestResponse response = new TestResponse();
        response.setData(request);
        response.setFlag(true);
        response.setMessage("BBBBB");
        return response;
    }

    /**
     * 接收消息有代理@SendToUser
     *
     * @param request
     * @param principal
     * @return@SendToUser 表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀。
     * 如下，最后消息会被发布在  /user/receive/test2-username。但是问题来了，这个username是怎么来的呢？就是通过 principal 参数来获得的。
     * 那么，principal 参数又是怎么来的呢？需要在spring-websocket 的配置类中重写 configureClientInboundChannel 方法，添加上用户的认证。
     */
    @MessageMapping("/message4")
    @SendToUser(value = "/receive/test2", broadcast = false)
    public TestResponse message4(TestRequest request, Principal principal) {
        String name = principal.getName();
        log.info("认证的名字是：{},Received message4: {}", name, JSON.toJSONString(request));
        //除了convertAndSend()以外，SimpMessageSendingOperations 还提供了convertAndSendToUser()方法。按照名字就可以判断出来，convertAndSendToUser()方法能够让我们给特定用户发送消息。
        simpMessageSendingOperations.convertAndSendToUser("bbb", "/receive/test2", request);
        TestResponse response = new TestResponse();
        response.setData(request);
        response.setFlag(true);
        response.setMessage("BBBBB");
        return response;
    }

//    /**
//     * 如上，这里虽然我还是用了认证的信息得到用户名。但是，其实大可不必这样，因为 convertAndSendToUser 方法可以指定要发送给哪个用户。
//     * 也就是说，完全可以把用户名的当作一个参数传递给控制器方法，从而绕过身份认证！convertAndSendToUser 方法最终会把消息发送到 /user/sername/queue/shouts 目的地上。
//     * @param shout
//     * @param stompHeaderAccessor
//     */
//    @MessageMapping("/singleShout")
//    public void singleUser(Shout shout, StompHeaderAccessor stompHeaderAccessor) {
//        String message = shout.getMessage();
//        LOGGER.info("接收到消息：" + message);
//        Principal user = stompHeaderAccessor.getUser();
//        simpMessageSendingOperations.convertAndSendToUser(user.getName(), "/queue/shouts", shout);
//    }

    /**
     * @param
     * @return
     * @SubscribeMapping 注解的控制器方法有返回值的话，返回值会直接发送到客户端，不经过代理。如果加上@SendTo 注解的话，则要经过消息代理。
     */
    @SubscribeMapping({"/subscribe"})
    public TestResponse subscribe() {
        log.info("Received subscribe");
        TestResponse response = new TestResponse();
        response.setData("subscribe");
        response.setFlag(true);
        response.setMessage("subscribe");
//        return "subscribe";
        return response;
    }


    /**
     * 处理消息异常
     * 在处理消息的时候，有可能会出错并抛出异常。因为STOMP消息异步的特点，发送者可能永远也不会知道出现了错误。
     *
     * @param t
     * @return
     * @MessageExceptionHandler标注的方法能够处理消息方法中所抛出的异常。我们可以把错误发送给用户特定的目的地上，然后用户从该目的地上订阅消息，从而用户就能知道自己出现了什么错误啦...
     */
    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception t) {
        t.printStackTrace();
        return t;
    }
}
