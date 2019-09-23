package com.happyloves.zc.service.account.api;

import com.happyloves.zc.service.account.dao.AccountJPA;
import com.happyloves.zc.service.account.entity.Account;
import com.happyloves.zc.service.account.openfeign.GoodsAPI;
import com.happyloves.zc.service.common.vo.AccountVO;
import com.happyloves.zc.service.common.vo.GoodsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:19
 * @Description:
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final AccountJPA jpa;
    private final GoodsAPI goodsAPI;

    /**
     * 注册保存
     *
     * @param req
     * @return
     */
    @PostMapping("/")
    public AccountVO save(@Valid @RequestBody AccountVO.LoginRequest req) {
        Account account = new Account();
        BeanUtils.copyProperties(req, account);
        var a = jpa.save(account);
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(a, vo);
        return vo;
    }

    /**
     * 查询所有账户
     *
     * @return
     */
    @GetMapping("/")
    public List<Account> getAll() {
        return jpa.findAll();
    }

    /**
     * 查询单个用户
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AccountVO getOne(@PathVariable int id) {
        Optional<Account> byId = jpa.findById(id);
        if (byId.isPresent()) {
            Account account = byId.get();
            AccountVO vo = new AccountVO();
            BeanUtils.copyProperties(account, vo);
            return vo;
        }
        return null;
    }

    /**
     * 支付扣款
     *
     * @param accountId
     * @param goodsId
     * @return
     */
    @PostMapping("/payment/{accountId}/{goodsId}")
    public Account payment(@PathVariable int accountId, @PathVariable int goodsId) {
        GoodsVO goodsVO = goodsAPI.getOne(goodsId);
        int pay = jpa.pay(accountId, goodsVO.getPrice());
        return jpa.findById(accountId).orElse(null);
    }

}
