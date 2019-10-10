package com.happyloves.zc.service.account.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:19
 * @Description:
 */
@Api(value = "Account-账户服务", tags = {"账户服务", "测试"}) //@Api注解放在类上面，这里的value是没用的，tags表示该controller的介绍。
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final DiscoveryClient discoveryClient;

    @GetMapping("/getDiscoveryClient")
    public List<ServiceInstance> getDiscoveryClient() {
        return discoveryClient.getInstances("account");
    }

    @GetMapping("/getServices")
    public List<String> getServices() {
        return discoveryClient.getServices();
    }
}
