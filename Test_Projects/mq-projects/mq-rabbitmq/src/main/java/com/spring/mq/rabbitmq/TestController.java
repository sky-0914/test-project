package com.spring.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 赵小胖
 * @Date 2019/7/24 19:42
 * @Description:
 */
@RestController
public class TestController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "send")
    public String send() {
        String content = "Date:" + System.currentTimeMillis();
        //发送默认交换机对应的的队列kinson
        amqpTemplate.convertAndSend("TEST1", content);
        return content;
    }
}
