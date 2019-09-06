package com.happyloves.zc.service.common.vo;

import lombok.Data;

/**
 * @Author 赵小胖
 * @Date 2019/9/7 00:56
 * @Description:
 */
@Data
public final class ComonResult<T> {
    private int code = 0;
    private String errorMsg;
    private T resultBody;

    public ComonResult() {
    }

    public ComonResult(T resultBody) {
        this.resultBody = resultBody;
    }
}
