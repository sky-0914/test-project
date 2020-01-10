package com.spring.cloud.transaction.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: zc
 * @Date: 2019-06-09 13:51
 * @Description:
 */
@FeignClient(name = "${goodsApi:goods-api}")
public interface GoodsFeign {
    @GetMapping(value = "/all")
    public Object getAll();
}
