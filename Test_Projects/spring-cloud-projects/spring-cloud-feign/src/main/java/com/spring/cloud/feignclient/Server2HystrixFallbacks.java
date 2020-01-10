package com.spring.cloud.feignclient;

import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2018/11/30 22:53
 * @Description:
 */
@Component
public class Server2HystrixFallbacks implements Server2Client {

    @Override
    public String spring1(String serviceName) {
        return "heihei";
    }
}
