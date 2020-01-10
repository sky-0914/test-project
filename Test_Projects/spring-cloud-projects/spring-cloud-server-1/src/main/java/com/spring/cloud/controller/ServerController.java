package com.spring.cloud.controller;

import com.spring.cloud.module.entity.User;
import com.spring.cloud.module.jpa.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Author: 赵小超
 * @Date: 2018/11/1 23:25
 * @Description:
 */
@RestController
public class ServerController {
    @Autowired
    private UserJpa jpa;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/server")
    public String test(Integer id) {
        String mes = "当前服务为 Server1 ======= ";
        if (null == id) {
            return mes += "参数不能为null";
        }
        //User user = jpa.getOne(id);
//        if (null == user) {
//            return mes += "查询的用户不存在";
//        }
//        return mes += "用户名为：" + user.getName() + " 年龄：" + user.getAge() + "岁";
        Optional<User> user = jpa.findById(id);
        if (user.isPresent()) {
            return mes += "用户名为：" + user.get().getName() + " 年龄：" + user.get().getAge() + "岁";
        }
        return mes += "查询的用户不存在";
    }

    @GetMapping(value = "/server/{id}")
    public String test1(@PathVariable int id) {
        String mes = "当前服务为 Server1 ======= ";
        if (0 == id) {
            return mes += "参数不能为null";
        }
        //User user = jpa.getOne(id);
//        if (null == user) {
//            return mes += "查询的用户不存在";
//        }
//        return mes += "用户名为：" + user.getName() + " 年龄：" + user.getAge() + "岁";
        Optional<User> user = jpa.findById(id);
        if (user.isPresent()) {
            return mes += "用户名为：" + user.get().getName() + " 年龄：" + user.get().getAge() + "岁";
        }
        return mes += "查询的用户不存在";
    }

    @GetMapping(value = "/server/test")
    public User test2(User user) {
        return user;
    }

    /**
     * 获取服务元数据
     *
     * @return
     */
    @GetMapping("/user-instance")
    public Object showInfo() {
        return this.discoveryClient.getInstances("server-1");
    }
}
