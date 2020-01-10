package com.spring.cloud.fallbackfactory;

import com.spring.cloud.feignclient.Server1Client;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2018/12/1 00:44
 * @Description:
 */
@Slf4j
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<Server1Client> {

    @Override
    public Server1Client create(Throwable throwable) {
        log.info("fallback; reason was:{}", throwable.getMessage());
        return new HystrixClientWithFallBackFactory() {
            @Override
            public String spring1() {
                return "ERROR";
            }
        };
    }
}
