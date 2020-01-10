package com.spring.cloud.apollo.api;

import com.spring.cloud.apollo.config.ApolloBeanConfig;
import com.spring.cloud.apollo.config.ApolloConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:30
 * @Description:
 */
@RestController
public class ClientController {
    @Resource
    private ApolloConfig apolloConfig;
    @Resource
    private ApolloBeanConfig apolloBeanConfig;

    @GetMapping(value = "/test1")
    public String test1() {
        System.out.println(apolloConfig.getAppName());
        String c = "test1返回 = aaa: %s,bbb: %s,ccc: %s,ddd: %s,env: %s";
        return String.format(c, apolloConfig.getAaa(), apolloConfig.getBbb(), apolloConfig.getCcc(), apolloConfig.getDdd(), apolloConfig.getEnv());
    }

    @GetMapping(value = "/test2")
    public String test2() {
        String c = "test2返回 = aaa: %s,bbb: %s,ccc: %s,ddd: %s";
        return String.format(c, apolloBeanConfig.getAaa(), apolloBeanConfig.getBbb(), apolloBeanConfig.getCcc(), apolloBeanConfig.getDdd());
    }
}
