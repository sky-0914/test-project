package com.spring.mq;


import com.spring.mq.mqtt.MqttGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MqttGateway mqttGateway;

    @RequestMapping("/sendMqtt")
    public String sendMqtt(String sendData, String topic) {
        mqttGateway.sendToMqtt(sendData, topic);
        return "OK";
    }
}