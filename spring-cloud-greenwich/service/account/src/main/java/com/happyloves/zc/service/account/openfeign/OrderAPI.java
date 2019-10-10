package com.happyloves.zc.service.account.openfeign;

import com.happyloves.zc.service.common.vo.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:21
 * @Description:
 */
@FeignClient(name = "order")
public interface OrderAPI {

    @GetMapping("/api/{id}")
    OrderVO getOne(@PathVariable int id);
}
