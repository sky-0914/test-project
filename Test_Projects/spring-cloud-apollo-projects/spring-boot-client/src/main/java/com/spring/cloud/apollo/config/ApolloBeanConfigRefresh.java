package com.spring.cloud.apollo.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 23:12
 * @Description: 监控配置Bean里的属性是否发生了变化，并且实现自动刷新
 */
@Slf4j
@Component
public class ApolloBeanConfigRefresh {
    @Autowired
    private ApolloBeanConfig apolloBeanConfig;
    @Autowired
    private org.springframework.cloud.context.scope.refresh.RefreshScope refreshScope;


    /**
     * 监控命名空间下的值是否发生的改变
     *
     * @param changeEvent
     * @ApolloConfigChangeListener 默认是监控Application的
     * @ApolloConfigChangeListener({"dev"})//监控哪个命名空间的名称
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean beanConfigChange = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("apollo")) {
                beanConfigChange = true;
                break;
            }
        }
        if (beanConfigChange) {
            log.info("apollo 配置Bean里的属性发生了改变");
            //刷新这个Bean的name
            refreshScope.refresh("apolloBeanConfig");
        }
    }
}