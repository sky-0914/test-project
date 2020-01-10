package com.spring.cloud.controller;

import com.spring.cloud.feignclient.Server1Client;
import com.spring.cloud.feignclient.Server2Client;
import com.spring.cloud.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 赵小超
 * @Date: 2018/11/7 11:09
 * @Description:
 */
@RestController
public class FeignController {

    @Autowired
    private Server1Client server1Client;
    @Autowired
    private Server2Client server2Client;

    @GetMapping("/feign")
    public String test(String id) {
        System.out.println(123);
        return server1Client.spring1(id);
    }

    @GetMapping("/feign/{id}")
    public String test1(@PathVariable String id) {
        System.out.println(123);
        return server1Client.spring2(id);
    }

    @GetMapping("/feign/test")
    public UserVO test2(UserVO userVO) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userVO.getId());
        map.put("name", userVO.getName());
        map.put("userName", userVO.getUserName());
        map.put("age", userVO.getAge());
        map.put("sex", userVO.getSex());
        return server1Client.spring3(map);
    }


    @GetMapping("/eureka/apps/{serviceName}")
    public String test3(@PathVariable String serviceName) {
        return server2Client.spring1(serviceName);
    }
}
