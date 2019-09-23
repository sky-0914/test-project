package com.happyloves.zc.service.order.openfeign;

import com.happyloves.zc.service.common.vo.GoodsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:21
 * @Description:
 */
@FeignClient(name = "goods")
public interface GoodsAPI {
    @GetMapping("/")
    List<GoodsVO> getAll();

    @GetMapping("/{id}")
    GoodsVO getOne(@PathVariable int id);
}
