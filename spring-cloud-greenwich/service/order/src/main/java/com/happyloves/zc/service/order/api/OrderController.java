package com.happyloves.zc.service.order.api;

import com.happyloves.zc.service.common.vo.GoodsVO;
import com.happyloves.zc.service.order.model.entity.order.Order;
import com.happyloves.zc.service.order.openfeign.GoodsAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zc
 * @Date: 2019/9/14 19:30
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final GoodsAPI goodsAPI;

    @PostMapping("/{account}/{goodsId}")
    public Order orders(@PathVariable int accountId, @PathVariable int goodsId) {
        GoodsVO goodsvo = goodsAPI.getOne(goodsId);
        return null;
    }

    @GetMapping("/{orderId}")
    public Order orders(@PathVariable int orderId) {
        log.info("被请求了");
        return Order.builder().id(orderId).no("xxxxx").build();
    }
}
