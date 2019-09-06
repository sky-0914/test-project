package com.happyloves.zc.service.account.openfeign;

import com.happyloves.zc.service.common.vo.GoodsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:21
 * @Description:
 */
@FeignClient(name = "goods")
public interface GoodsAPI {
    @GetMapping("/goods")
    List<GoodsVO> getAll();
}
