package com.happyloves.zc.service.account.openfeign;

import com.happyloves.zc.service.account.config.GoodsFeignConfig;
import com.happyloves.zc.service.common.vo.GoodsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:21
 * @Description: @FeignClient configuration = GoodsFeignConfig.class 细粒度配置，指定配置类
 */
//@FeignClient(name = "goods", configuration = GoodsFeignConfig.class) //细粒度-代码实现
@FeignClient(name = "goods") //细粒度-配置属性
public interface GoodsAPI {
    @GetMapping("/api/")
    List<GoodsVO> getAll();

    @GetMapping("/api/{id}")
    GoodsVO getOne(@PathVariable int id);
}
