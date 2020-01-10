package com.spring.cloud.transaction.api.service;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.transaction.vo.GoodsResult;
import com.spring.cloud.transaction.vo.GoodsTest1VO.GoodsTestt1RequestVO;
import com.yiqiniu.easytrans.protocol.tcc.TccMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 赵小超
 * @Date: 2019/6/10 21:32
 * @Description:
 */
@Slf4j
@Component
public class Tests1Service implements TccMethod<GoodsTestt1RequestVO, GoodsResult> {
    public static final String METHOD_NAME = "test1";

    @Override
    public GoodsResult doTry(GoodsTestt1RequestVO goodsTestt1RequestVO) {
        log.info("test1---doTry,{}", JSON.toJSONString(goodsTestt1RequestVO));
        if (goodsTestt1RequestVO.isFlag()) {
            return new GoodsResult(true, null, null);
        } else {
            return new GoodsResult(false, "出错啦", null);
        }
    }

    @Override
    public void doConfirm(GoodsTestt1RequestVO goodsTestt1RequestVO) {
        log.info("test1---doConfirm");
    }

    @Override
    public void doCancel(GoodsTestt1RequestVO goodsTestt1RequestVO) {
        log.info("test1---doCancel");
    }

    @Override
    public int getIdempotentType() {
        return IDENPOTENT_TYPE_FRAMEWORK;
    }
}
