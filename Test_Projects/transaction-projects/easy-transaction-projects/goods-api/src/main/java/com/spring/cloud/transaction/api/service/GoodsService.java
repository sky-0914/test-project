package com.spring.cloud.transaction.api.service;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.transaction.vo.GoodsConsumptionRequestCfg;
import com.spring.cloud.transaction.vo.TestService;
import com.yiqiniu.easytrans.core.EasyTransFacade;
import com.yiqiniu.easytrans.protocol.BusinessProvider;
import com.yiqiniu.easytrans.protocol.tcc.EtTcc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: zc
 * @Date: 2019-06-10 17:36
 * @Description:
 */
@Slf4j
@Component
public class GoodsService {
    @Resource
    private EasyTransFacade transaction;

    /**
     * 如果doTryPay的入参为集成了EasyTransRequest并带有BusinessIdentiffer的话则无需指定cfgClass
     */
    @Transactional
    @EtTcc(confirmMethod = "doConfirmPay", cancelMethod = "doCancelPay", idempotentType = BusinessProvider.IDENPOTENT_TYPE_FRAMEWORK, cfgClass = GoodsConsumptionRequestCfg.class)
    public TestService.GoodsConsumptionResponseVO doTryPay(TestService.GoodsConsumptionRequestVO param) {
        log.info("doTryPay");
        log.info(JSON.toJSONString(param));
        if (param.isFlag()) {
            TestService.GoodsConsumptionResponseVO goodsConsumptionResponseVO = new TestService.GoodsConsumptionResponseVO();
            goodsConsumptionResponseVO.setRes("bbbbbbbbbbbbb");
            return goodsConsumptionResponseVO;
        }
        log.info("报错啦");
        throw new RuntimeException("can not find specific user id or have not enought money");
    }

    @Transactional
    public void doConfirmPay(TestService.GoodsConsumptionRequestVO param) {
        log.info("doConfirmPay");
    }

    @Transactional
    public void doCancelPay(TestService.GoodsConsumptionRequestVO param) {
        log.info("doCancelPay");
    }
}
