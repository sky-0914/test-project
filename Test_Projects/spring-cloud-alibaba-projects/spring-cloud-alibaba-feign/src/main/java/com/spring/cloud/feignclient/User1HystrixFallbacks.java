package com.spring.cloud.feignclient;

import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2018/11/30 22:53
 * @Description:
 */
@Component
public class User1HystrixFallbacks implements User1Client {
    @Override
    public String test1(int id) {
        return "user-test1-"+id;
    }

    @Override
    public String test2(int id) {
        return "user-test2-"+id;
    }
}
