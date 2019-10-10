package com.happyloves.zc.service.account.api;

import com.happyloves.zc.service.account.dao.AccountJPA;
import com.happyloves.zc.service.account.entity.Account;
import com.happyloves.zc.service.account.openfeign.GoodsAPI;
import com.happyloves.zc.service.account.openfeign.OrderAPI;
import com.happyloves.zc.service.common.vo.AccountVO;
import com.happyloves.zc.service.common.vo.AccountVO.LoginRequest;
import com.happyloves.zc.service.common.vo.ApiMessage;
import com.happyloves.zc.service.common.vo.GoodsVO;
import com.happyloves.zc.service.common.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:19
 * @Description:
 */
@Api(value = "Account-账户服务", tags = {"账户服务"}) //@Api注解放在类上面，这里的value是没用的，tags表示该controller的介绍。
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final AccountJPA jpa;
    private final GoodsAPI goodsAPI;
    private final OrderAPI orderAPI;

    /**
     * 注册保存
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "注册保存", notes = "注册保存新账户并返回所有账户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "req", required = true, value = "登陆注册", paramType = "body", dataType = "登陆实体参数"),
    })
    @PostMapping("/")
    public ApiMessage<AccountVO> save(@Valid @RequestBody LoginRequest req) {
        Account account = new Account();
        BeanUtils.copyProperties(req, account);
        var a = jpa.save(account);
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(a, vo);
        return new ApiMessage<>(vo);
    }

    /**
     * 查询所有账户
     *
     * @return
     */
    @ApiOperation(value = "查询所有账户")
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
    @ApiOperation(value = "获取用户信息", notes = "获取某一个用户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", required = true, value = "用户ID", paramType = "path", dataType = "string"),
    })
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
    @ApiOperation(value = "支付扣款")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "accountId", required = true, value = "用户ID", paramType = "path", dataType = "string"),
            @ApiImplicitParam(name = "goodsId", required = true, value = "商品ID", paramType = "path", dataType = "string"),
    })
    @PostMapping("/payment/{accountId}/{goodsId}")
    public Account payment(@Min(1) @PathVariable int accountId, @Min(1) @PathVariable int goodsId) throws Exception {
        GoodsVO goodsVO = goodsAPI.getOne(goodsId);
        int pay = jpa.pay(accountId, goodsVO.getPrice());
        if (pay == 0) {
            throw new Exception("扣款失败！");
        }
        return jpa.findById(accountId).orElse(null);
    }

    @GetMapping("/order/{orderId}")
    public OrderVO order(@Min(1) @PathVariable int orderId) {
        return orderAPI.getOne(orderId);
    }

}
