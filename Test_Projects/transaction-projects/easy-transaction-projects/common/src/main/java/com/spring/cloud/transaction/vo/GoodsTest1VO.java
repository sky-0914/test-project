package com.spring.cloud.transaction.vo;

import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.tcc.TccMethodRequest;
import lombok.Data;

/**
 * @Author: 赵小超
 * @Date: 2019/6/10 21:30
 * @Description:
 */
public class GoodsTest1VO {
    @Data
    @BusinessIdentifer(appId = "goods-api", busCode = "test1", rpcTimeOut = 2000)
    public static class GoodsTestt1RequestVO implements TccMethodRequest<GoodsResult> {

        private static final long serialVersionUID = 1L;
        private int goodsId, count;
        private String req;
        private boolean flag;
    }
}
