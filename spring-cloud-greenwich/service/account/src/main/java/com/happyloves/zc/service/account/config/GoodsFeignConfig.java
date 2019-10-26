package com.happyloves.zc.service.account.config;//package com.happyloves.zc.service.account.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Author 赵小胖
 * @Date 2019/10/10 21:22
 * @Description: Feign配置类
 */
public class GoodsFeignConfig {
    @Bean
    public Logger.Level Logger() {
        return Logger.Level.FULL;
    }
}
