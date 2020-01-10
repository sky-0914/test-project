package com.spring.cloud.alibaba.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zc
 * @Date: 2019/1/28 18:02
 * @Description:
 */
@Data
public class User implements Serializable {
    private int id;
    private String name;
    private int age;

    public User(int id) {
        this.id = id;
        this.name = "张三";
        this.age = 18;
    }

    public User() {
    }
}
