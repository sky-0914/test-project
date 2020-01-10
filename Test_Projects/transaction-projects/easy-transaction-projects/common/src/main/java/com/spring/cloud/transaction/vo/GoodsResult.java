package com.spring.cloud.transaction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 赵小超
 * @Date: 2019/6/10 21:46
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String message;
    private Object data;
}
