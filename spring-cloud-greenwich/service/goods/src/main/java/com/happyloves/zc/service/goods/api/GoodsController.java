package com.happyloves.zc.service.goods.api;

import com.happyloves.zc.service.goods.dao.GoodsJPA;
import com.happyloves.zc.service.goods.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:19
 * @Description:
 */
@Slf4j
@RestController
public class GoodsController {

    @Autowired
    private GoodsJPA jpa;

    @GetMapping("/")
    public List<Goods> getAll() {
        return jpa.findAll();
    }

    @GetMapping("/{id}")
    public Goods getOne(@PathVariable int id) {
        return jpa.getOne(id);
    }

    @PutMapping("/inventory/{id}")
    public Goods inventory(@PathVariable int id, int count) throws Exception {
        int i = jpa.inventory(id, count);
        if (i == 0) {
            throw new Exception("库存不足！");
        }
        return jpa.getOne(id);
    }
}
