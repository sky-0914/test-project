package com.spring.cloud.controller;

import com.spring.cloud.feignclient.Server1Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵小超
 * @Date: 2018/11/7 11:09
 * @Description:
 */
@RestController
public class FeignController {

    @Autowired
    private Server1Client server1Client;

    @GetMapping("/feign")
    public String test(String id) {
        System.out.println(123);
        return server1Client.spring1();
    }

}
