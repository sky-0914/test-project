package com.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵小超
 * @Date: 2018/11/1 23:25
 * @Description:
 */
@RestController
public class ServerController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/server")
    public String test(Integer id) {
        String mes = "当前服务为 Server2 ======= ";
        return mes;
    }

    @GetMapping(value = "/server/{id}")
    public String test1(@PathVariable Integer id) {
        String mes = "当前服务为 Server2 ======= ";
        return mes;
    }

}
