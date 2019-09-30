package com.happyloves.zc.service.order.api;

import com.happyloves.zc.service.common.vo.GoodsVO;
import com.happyloves.zc.service.order.model.entity.order.Order;
import com.happyloves.zc.service.order.openfeign.GoodsAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zc
 * @Date: 2019/9/14 19:30
 * @Description:
 */
@RequestMapping("/")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final GoodsAPI goodsAPI;

    @PostMapping("/{account}/{goodsId}")
    public Order orders(@PathVariable int accountId, @PathVariable int goodsId) {

        GoodsVO goodsvo = goodsAPI.getOne(goodsId);

        return null;
    }
}
