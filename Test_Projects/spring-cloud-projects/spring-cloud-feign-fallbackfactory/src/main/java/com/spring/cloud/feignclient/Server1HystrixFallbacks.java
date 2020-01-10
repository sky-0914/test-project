package com.spring.cloud.feignclient;

import com.spring.cloud.vo.UserVO;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: 赵小超
 * @Date: 2018/11/30 22:53
 * @Description:
 */
@Component
public class Server1HystrixFallbacks implements Server1Client {
    @Override
    public String spring1() {
        return "111111111";
    }
}
