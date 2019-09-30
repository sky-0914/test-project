package com.happyloves.zc.service.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 赵小胖
 * @Date 2019/9/7 00:56
 * @Description:
 */
@ApiModel(value = "API接口返回值")
@Data
public final class ApiMessage<T> {
    @ApiModelProperty(value = "状态码")
    private int code = 0;
    @ApiModelProperty(value = "是否正确返回")
    private boolean succeed = true;
    @ApiModelProperty(value = "错误信息")
    private String errorMsg;
    @ApiModelProperty(value = "接口返回消息体")
    private T resultBody;

    public ApiMessage() {
    }

    public ApiMessage(T resultBody) {
        this.resultBody = resultBody;
    }
}
