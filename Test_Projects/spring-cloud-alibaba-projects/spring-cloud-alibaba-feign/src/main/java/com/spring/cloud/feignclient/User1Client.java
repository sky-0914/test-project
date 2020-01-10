package com.spring.cloud.feignclient;

import com.spring.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: 赵小超
 * @Date: 2018/11/7 11:11
 * @Description:
 */
@FeignClient(name = "user",configuration = FeignConfig.class, fallback = User1HystrixFallbacks.class)
public interface User1Client {
    /**
     * 使用Spring注解配置
     * <p>
     * 坑1.必须按照原始的写法 @RequestMapping
     * 坑2.传参数注解得设置value
     * 坑3.参数如果是对象默认是POST请求。用map传多个GET请求参数
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/test/{id}")
    String test1(@PathVariable("id") int id);

    @PostMapping(value = "/test/{id}")
    String test2(@PathVariable("id") int id);

    /**
     * 使用Feign注解配置
     *
     * @param id
     * @return
     */
//    @RequestLine("GET /server?id={id}")
//    String feign1(@Param("id") String id);
//
//    @RequestLine("GET /server/{id}")
//    String feign2(@Param("id") String id);
//
//    // 不支持GET 这样传参，只能以第一种方式传递多个参数
//    @RequestLine("GET /server/test")
//    UserVO feign3(UserVO user);

}
