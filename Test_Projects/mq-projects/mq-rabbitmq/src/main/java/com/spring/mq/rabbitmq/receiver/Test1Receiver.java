package com.spring.mq.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
//监听队列kinson
@RabbitListener(queues = {"TEST1"})
public class Test1Receiver {

    @RabbitHandler
    public void receiver(String msg) {
        System.out.println("MyReceiver1 :" + msg);
    }
}
