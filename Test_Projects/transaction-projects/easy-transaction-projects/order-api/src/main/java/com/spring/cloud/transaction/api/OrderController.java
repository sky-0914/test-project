package com.spring.cloud.transaction.api;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.transaction.api.feign.GoodsFeign;
import com.spring.cloud.transaction.api.repository.OrderRepository;
import com.spring.cloud.transaction.vo.GoodsResult;
import com.spring.cloud.transaction.vo.GoodsTest1VO.GoodsTestt1RequestVO;
import com.spring.cloud.transaction.vo.GoodsTest2VO.GoodsTestt2RequestVO;
import com.spring.cloud.transaction.vo.TestService;
import com.yiqiniu.easytrans.core.EasyTransFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:30
 * @Description:
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private GoodsFeign goodsFeign;

    @GetMapping(value = "/all")
    public Object getAll() {
        return repository.findAll();
    }

//    @PostMapping(value = "/buy/{userId}/{goodsId}")
//    public Object buy(@PathVariable int userId, @PathVariable int goodsId) {
//        return goodsFeign.getAll();
//    }

    @Resource
    private TestService service;

    @Transactional
    @PostMapping(value = "/buy/{userId}/{goodsId}")
    public Object test(@PathVariable int userId, @PathVariable int goodsId, boolean flag) throws Exception {
        TestService.GoodsConsumptionRequestVO goodsConsumptionRequestVO = new TestService.GoodsConsumptionRequestVO();
        goodsConsumptionRequestVO.setCount(10);
        goodsConsumptionRequestVO.setGoodsId(10);
        goodsConsumptionRequestVO.setReq("aaaaaaaa");
        goodsConsumptionRequestVO.setFlag(flag);
        TestService.GoodsConsumptionResponseVO consumption = service.pay(goodsConsumptionRequestVO);
        System.out.println(JSON.toJSONString(consumption));
        if (goodsId != 1) {
            throw new RuntimeException("can not find specific user id or have not enought money");
        }
        return goodsFeign.getAll();
    }

    @Resource
    private EasyTransFacade transaction;

    @Transactional
    @PostMapping(value = "/test/{userId}/{goodsId}")
    public Object test1(@PathVariable int userId, @PathVariable int goodsId, boolean flag) throws ExecutionException, InterruptedException {
        /**
         * finish the local transaction first, in order for performance and generated of business id
         *
         * 优先完成本地事务以 1. 提高性能（减少异常时回滚消耗）2. 生成事务内交易ID
         */
        Integer id = (int) (1 + Math.random() * (10 - 1 + 1));

        /**
         * annotation the global transactionId, it is combined of appId + bussiness_code + id
         * it can be omit,then framework will use "default" as businessCode, and will generate an id
         * but it will make us harder to associate an global transaction to an concrete business
         *
         * 声明全局事务ID，其由appId,业务代码，业务代码内ID构成
         * 本代码可以省略，框架会自动生成ID及使用一个默认的业务代码
         * 但这样的话，会使得我们难以把全局事务ID与一个具体的事务关联起来
         */
        transaction.startEasyTrans("test1", id);

        /**
         * call remote service to deduct money, it's a TCC service,
         * framework will maintains the eventually constancy based on the final transaction status of method buySomething
         * if you think introducing object transaction(EasyTransFacade) is an unacceptable coupling
         * then you can refer to another demo(interfacecall) in the demos directory, it will show you how to execute transaction by user defined interface
         *
         * 调用远程服务扣除所需的钱,这个远程服务实现了TCC接口,
         * 框架会根据buySomething方法的事务结果来维护远程服务的最终一致性
         * 如果你认为引入transaction（EasyTransFacde）是一个无法接受的耦合
         * 那么你可以参考在demos目录下另外一个样例(interfacecall)，它会告诉你如何用用户自定义的接口来执行远程事务
         */
        GoodsTestt1RequestVO test1 = new GoodsTestt1RequestVO();
        test1.setFlag(flag);
        test1.setReq("test1");
        /**
         * return future for the benefits of performance enhance(batch write execute log and batch execute RPC)
         * 返回future是为了能方便的优化性能(批量写日志及批量调用RPC)
         */
//        @SuppressWarnings("unused")
        Future<GoodsResult> test1Res = transaction.execute(test1);

        GoodsTestt2RequestVO test2 = new GoodsTestt2RequestVO();
        test2.setFlag(flag);
        test2.setReq("test2");
        Future<GoodsResult> test2Res = transaction.execute(test2);

        log.info(JSON.toJSONString(test1Res.get()));
        log.info(JSON.toJSONString(test2Res.get()));
        if (!test1Res.get().isSuccess() || !test2Res.get().isSuccess()) {
            throw new RuntimeException("aaaaaaaaaaaaaaaaa");
        }

        if (goodsId != 1) {
            throw new RuntimeException("bbbbbbbbbbbbbbbbbbbbbb");
        }

        /**
         * you can add more types of transaction calls here, e.g. TCC,reliable message, SAGA-TCC and so on
         * framework will maintains the eventually consistent
         *
         * 你可以额外加入其它类型的事务，如TCC,可靠消息，SAGA-TCC等等
         * 框架会维护全局事务的最终一致性
         */


        /**
         * we can get remote service result to determine whether to commit this transaction
         *
         * 可以获取远程返回的结果用以判断是继续往下走 还是 抛异常结束
         *
         * deductFuture.get();
         */

        return id;
    }

}
