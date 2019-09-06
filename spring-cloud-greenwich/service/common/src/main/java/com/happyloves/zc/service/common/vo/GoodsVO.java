package com.happyloves.zc.service.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:25
 * @Description:
 */
@Data
public class GoodsVO extends BaseVO {
    private int id;
    private String name;
    private int inventory;
    private int price;
}
