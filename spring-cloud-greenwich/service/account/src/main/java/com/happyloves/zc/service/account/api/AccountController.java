package com.happyloves.zc.service.account.api;

import com.happyloves.zc.service.account.dao.AccountJPA;
import com.happyloves.zc.service.account.entity.Account;
import com.happyloves.zc.service.account.openfeign.GoodsAPI;
import com.happyloves.zc.service.common.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:19
 * @Description:
 */
@Slf4j
@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private AccountJPA jpa;

    @Autowired
    private GoodsAPI goodsAPI;

    @GetMapping("/")
    public List<Account> getAll() {
        return jpa.findAll();
    }

    @GetMapping("/{id}")
    public Account getOne(@PathVariable int id) {
        return jpa.getOne(id);
    }

    @PutMapping("/goods")
    public List<Account> pay() {
        List<GoodsVO> goodsVOList = goodsAPI.getAll();
        log.info(goodsVOList.toString());
        return null;
    }

}
