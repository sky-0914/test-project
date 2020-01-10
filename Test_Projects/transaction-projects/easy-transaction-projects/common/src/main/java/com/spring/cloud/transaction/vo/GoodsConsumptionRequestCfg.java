package com.spring.cloud.transaction.vo;

import com.spring.cloud.transaction.vo.TestService.GoodsConsumptionRequestVO;
import com.spring.cloud.transaction.vo.TestService.GoodsConsumptionResponseVO;
import com.yiqiniu.easytrans.protocol.BusinessIdentifer;
import com.yiqiniu.easytrans.protocol.tcc.TccMethodRequest;

/**
 * @Author: zc
 * @Date: 2019-06-10 15:55
 * @Description:
 */
@BusinessIdentifer(appId = "goods-api", busCode = "pay", rpcTimeOut = 2000)
public class GoodsConsumptionRequestCfg extends GoodsConsumptionRequestVO implements TccMethodRequest<GoodsConsumptionResponseVO> {
    private static final long serialVersionUID = 1L;
}
