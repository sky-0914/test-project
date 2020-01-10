package com.spring.cloud.transaction.api.service;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.transaction.vo.GoodsResult;
import com.spring.cloud.transaction.vo.GoodsTest2VO.GoodsTestt2RequestVO;
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
public class Tests2Service implements TccMethod<GoodsTestt2RequestVO, GoodsResult> {
    public static final String METHOD_NAME = "test2";

    @Override
    public GoodsResult doTry(GoodsTestt2RequestVO goodsTestt2RequestVO) {
        log.info("test2---doTry,{}", JSON.toJSONString(goodsTestt2RequestVO));
        if (goodsTestt2RequestVO.isFlag()) {
            return new GoodsResult(true, null, null);
        } else {
            return new GoodsResult(false, "出错啦", null);
        }
    }

    @Override
    public void doConfirm(GoodsTestt2RequestVO goodsTestt1RequestVO) {
        log.info("test2---doConfirm");
    }

    @Override
    public void doCancel(GoodsTestt2RequestVO goodsTestt1RequestVO) {
        log.info("test2---doCancel");
    }

    @Override
    public int getIdempotentType() {
        return IDENPOTENT_TYPE_FRAMEWORK;
    }
}
