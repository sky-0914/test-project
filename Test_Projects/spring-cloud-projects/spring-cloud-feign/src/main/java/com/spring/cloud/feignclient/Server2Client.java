package com.spring.cloud.feignclient;

import com.spring.config.FeignConfig2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: 赵小超
 * @Date: 2018/11/7 11:11
 * @Description: value 指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现
 * configuration Feign配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract
 * fallback: 定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口
 * url 还可使用url属性指定请求的URL（URL可以是完整的URL或主机名），例如@FeignClient(name = "abcde", url = "http://localhost:8000/") 。此时，name可以是任意值，但不可省略，否则应用将无法启动！
 */
@FeignClient(name = "xx", url = "http://localhost:10001/", configuration = FeignConfig2.class, fallback = Server2HystrixFallbacks.class)
public interface Server2Client {
    /**
     * 使用Spring注解配置
     * <p>
     * 坑1.必须按照原始的写法 @RequestMapping
     * 坑2.传参数注解得设置value
     * 坑3.参数如果是对象默认是POST请求。用map传多个GET请求参数
     *
     * @param serviceName
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eureka/apps/{serviceName}")
    String spring1(@PathVariable("serviceName") String serviceName);

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
