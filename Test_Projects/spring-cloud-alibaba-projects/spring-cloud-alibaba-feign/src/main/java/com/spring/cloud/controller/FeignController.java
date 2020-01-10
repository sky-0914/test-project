package com.spring.cloud.controller;

import com.spring.cloud.feignclient.User1Client;
import com.spring.cloud.feignclient.User2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵小超
 * @Date: 2018/11/7 11:09
 * @Description:
 */
@RestController
public class FeignController {

    @Autowired
    private User1Client user1Client;
    @Autowired
    private User2Client user2Client;

    @GetMapping(value = "/test/{id}")
    public String test1(@PathVariable int id) {
        return user1Client.test1(id);
    }

    @PostMapping(value = "/test/{id}")
    public String test2(@PathVariable int id) {
        return user1Client.test2(id);
    }

    @GetMapping(value = "/config/test1")
    public String test1() {
        return user2Client.config1();
    }

    @GetMapping(value = "/config/test2")
    public String test2() {
        return user2Client.config2();
    }
}
