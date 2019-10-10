package com.happyloves.zc.service.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:26
 * @Description:
 * @Null 被注释的元素必须为null
 * @NotNull 被注释的元素不能为null
 * @AssertTrue 被注释的元素必须为true
 * @AssertFalse 被注释的元素必须为false
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max,min) 被注释的元素的大小必须在指定的范围内。
 * @Digits(integer,fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 * @Pattern(value) 被注释的元素必须符合指定的正则表达式。
 * @Email 被注释的元素必须是电子邮件地址
 * @Length 被注释的字符串的大小必须在指定的范围内
 * @NotEmpty 被注释的字符串必须非空
 * @Range 被注释的元素必须在合适的范围内
 */
@ApiModel("账户实体")
@Data
public class AccountVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 4601009988055817869L;
    @ApiModelProperty(value = "名称",position = 1)
    @NotBlank(message = "名称不能为空")
    private String name;
    @ApiModelProperty(value = "年龄",position = 1)
    @Min(value = 1, message = "年龄不能小于等于0")
    @Max(value = 200, message = "年龄不能大于200")
    private int age;
    @ApiModelProperty(value = "余额",position = 1)
    private int balance;

    @ApiModel("登陆实体参数")
    @Data
    public static class LoginRequest {
        @ApiModelProperty(value = "名称",position = 1)
        @NotBlank(message = "名称不能为空")
        private String name;
        @ApiModelProperty(value = "年龄",position = 2)
        @Min(value = 1, message = "年龄不能小于等于0")
        @Max(value = 200, message = "年龄不能大于200")
        private int age;
    }
}
