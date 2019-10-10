package com.happyloves.zc.service.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 赵小胖
 * @Date 2019/10/10 21:37
 * @Description:
 */
@Configuration
public class GoodsRibbonRuleConfig {
    @Bean
    public IRule ribbonRulr() {
        return new RandomRule();
    }

//    @Bean
//    public IPing iPing(){
//        return new PingUrl();
//    }
}
