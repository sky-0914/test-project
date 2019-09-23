package com.happyloves.zc.service.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 赵小胖
 * @Date 2019/9/23 17:50
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1692171653448424910L;

    /**
     * 订单号
     */
    private String no;

    /**
     * 总价
     */
    private Double totalPrice;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态0：下单未支付。1：支付完成交易成功。2：交易失败
     */
    private Integer state;

    /**
     * 用户唯一标识
     */
    private Integer accountId;
}
