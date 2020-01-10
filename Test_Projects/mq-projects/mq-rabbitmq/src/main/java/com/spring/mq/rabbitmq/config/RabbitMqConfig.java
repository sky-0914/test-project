package com.spring.mq.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @author 赵小胖
 */
public class RabbitMqConfig {

    @Bean
    public Queue mqttQueue() {
        return new Queue("TEST1");
    }
}
