package com.happyloves.zc.service.order.api;

import com.happyloves.zc.service.order.model.entity.order.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zc
 * @Date: 2019/9/14 19:30
 * @Description:
 */
@RequestMapping("/order")
@RestController
public class OrderController {



    @PostMapping("/order/{goodsId}")
    public Order placeTheOrder(){

    }
}
