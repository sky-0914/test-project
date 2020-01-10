package com.spring.cloud.alibaba.api;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.alibaba.config.ValueConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:30
 * @Description:
 */
@RestController
public class ClientController {

    @Value("${test.aaa:111}")
    private String aaa;
    @Value("${test.bbb:222}")
    private String bbb;
    @Value("${test.ccc:333}")
    private String ccc;
    @Value("${test.ddd:444}")
    private String ddd;


    @GetMapping(value = "/test/{id}")
    public String test1(@PathVariable int id) {
        return JSON.toJSONString(new User(id));
    }

    @PostMapping(value = "/test/{id}")
    public String test2(@PathVariable int id) {
        return JSON.toJSONString(new User(id));
    }

    @Resource
    private ValueConfig valueConfig;
    @Resource
    private com.spring.cloud.alibaba.config.BeanConfig beanConfig;

    @GetMapping(value = "/config/test1")
    public String test1() {
        String c = "test1返回 = aaa: %s,bbb: %s,ccc: %s,ddd: %s";
        return String.format(c, valueConfig.getAaa(), valueConfig.getBbb(), valueConfig.getCcc(), valueConfig.getDdd());
    }

    @GetMapping(value = "/config/test2")
    public String test2() {
        String c = "test2返回 = aaa: %s,bbb: %s,ccc: %s,ddd: %s";
        return String.format(c, beanConfig.getAaa(), beanConfig.getBbb(), beanConfig.getCcc(), beanConfig.getDdd());
    }

}
