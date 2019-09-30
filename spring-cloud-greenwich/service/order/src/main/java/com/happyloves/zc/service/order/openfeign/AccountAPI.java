package com.happyloves.zc.service.order.openfeign;

import com.happyloves.zc.service.common.vo.AccountVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 赵小胖
 * @Date 2019/9/6 19:21
 * @Description:
 */
@FeignClient(name = "account")
public interface AccountAPI {

    @GetMapping("/{id}")
    AccountVO getOne(@PathVariable int id);
}
