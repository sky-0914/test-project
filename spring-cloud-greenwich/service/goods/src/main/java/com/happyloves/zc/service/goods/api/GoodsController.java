package com.happyloves.zc.service.goods.api;

import com.happyloves.zc.service.goods.dao.GoodsJPA;
import com.happyloves.zc.service.goods.entity.Goods;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:19
 * @Description:
 */
@Api(tags = {"商品服务"})
@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoodsController {

    private final GoodsJPA jpa;

    @ApiOperation(value = "查询所有商品信息")
    @GetMapping("/")
    public List<Goods> getAll() {
        return jpa.findAll();
    }

    @ApiOperation(value = "查询单个商品")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", required = true, value = "商品ID", paramType = "path", dataType = "string"),
    })
    @GetMapping("/{id}")
    public Goods getOne(@PathVariable int id) {
        return jpa.getOne(id);
    }

    @ApiOperation(value = "扣除库存")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", required = true, value = "商品ID", paramType = "path", dataType = "string"),
            @ApiImplicitParam(name = "id", required = true, value = "商品ID", paramType = "query", dataType = "string"),
    })
    @PutMapping("/inventory/{id}")
    public Goods inventory(@PathVariable int id, int count) throws Exception {
        int i = jpa.inventory(id, count);
        if (i == 0) {
            throw new Exception("库存不足！");
        }
        return jpa.getOne(id);
    }
}
