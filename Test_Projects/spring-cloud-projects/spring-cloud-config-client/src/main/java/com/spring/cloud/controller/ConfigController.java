package com.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵小超
 * @Date: 2019/1/5 23:29
 * @Description:
 */
@RestController
public class ConfigController {

    @Value("${prefile}")
    private String prefile;
    @Value("${foo:test}")
    private String foo;
    @Value("${bar:test}")
    private String bar;

    @GetMapping(value = "test")
    public Object test() {
        return prefile + ", " + foo + ", " + bar;
    }
}
