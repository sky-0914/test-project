package com.spring.cloud.feignclient;

import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2018/11/30 22:53
 * @Description:
 */
@Component
public class User2HystrixFallbacks implements User2Client {

    @Override
    public String config1() {
        return "user-config1";
    }

    @Override
    public String config2() {
        return "user-config2";
    }
}
