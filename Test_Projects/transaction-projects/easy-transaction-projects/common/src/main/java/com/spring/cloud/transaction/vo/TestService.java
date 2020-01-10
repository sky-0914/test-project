package com.spring.cloud.transaction.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zc
 * @Date: 2019-06-10 14:59
 * @Description:
 */
public interface TestService {

    GoodsConsumptionResponseVO pay(GoodsConsumptionRequestVO request);

    @Data
    public static class GoodsConsumptionRequestVO implements Serializable {

        private static final long serialVersionUID = 1L;
        private int goodsId, count;
        private String req;
        private boolean flag;
    }

    @Data
    public static class BaseResponseVO implements Serializable {
        private boolean success;
    }

    @Data
    public static class GoodsConsumptionResponseVO extends BaseResponseVO implements Serializable {
        private static final long serialVersionUID = 1L;
        private String res;

        @Override
        public String toString() {
            return "1111111122211111";
        }
    }
}
