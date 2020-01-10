package com.spring.mq.redis;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author 赵小胖
 * @Date 2019/8/2 16:21
 * @Description:
 */
@Data
public class TestVO implements Serializable {
    private static final long serialVersionUID = -560035975886178417L;
    private String name;
}
