package com.happyloves.zc.service.common.config;

import java.lang.annotation.*;

/**
 * @Author: zc
 * @Date: 2019/10/28 19:54
 * @Description:
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Test {
    public String  test() default "";
}
