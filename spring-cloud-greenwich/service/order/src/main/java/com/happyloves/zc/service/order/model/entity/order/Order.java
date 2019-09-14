package com.happyloves.zc.service.order.model.entity.order;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
public class Order implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 订单号
     */
    private String no;

    /**
     * 总价
     */
    @Column(name = "total_price")
    private Double totalPrice;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private Double unitPrice;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态0：下单未支付。1：支付完成交易成功。2：交易失败
     */
    private Integer state;

    /**
     * 用户唯一标识
     */
    @Column(name = "account_id")
    private Integer accountId;

    private static final long serialVersionUID = 1L;
}