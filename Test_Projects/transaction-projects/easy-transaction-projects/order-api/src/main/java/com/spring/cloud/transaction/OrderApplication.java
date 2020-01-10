package com.spring.cloud.transaction;

import com.spring.cloud.transaction.vo.GoodsConsumptionRequestCfg;
import com.spring.cloud.transaction.vo.TestService;
import com.yiqiniu.easytrans.EnableEasyTransaction;
import com.yiqiniu.easytrans.util.CallWrapUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 赵小超
 */
@EnableEasyTransaction
@EnableTransactionManagement
@EnableFeignClients
@EnableEurekaClient
//@EnableSwagger2Doc
@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public TestService payService(CallWrapUtil util) {
        return util.createTransactionCallInstance(TestService.class, GoodsConsumptionRequestCfg.class);
    }
}
