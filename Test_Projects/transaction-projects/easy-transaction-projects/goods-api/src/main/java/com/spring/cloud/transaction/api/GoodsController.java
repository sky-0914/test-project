package com.spring.cloud.transaction.api;

import com.spring.cloud.transaction.api.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 赵小超
 * @Date: 2019/1/17 21:30
 * @Description:
 */
@Slf4j
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository repository;

    @GetMapping(value = "/all")
    public Object getAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/{goodsId}")
    public Object getOne(@PathVariable int goodsId) {
        return repository.findById(goodsId).get();
    }

    @Transactional
    @PutMapping(value = "/consumption/{goodsId}")
    public Object consumption(@PathVariable int goodsId, int count) throws Exception {
        int i = repository.consumption(goodsId, count);
        if (i != 1) {
            log.error("库存不足");
            throw new Exception("库存不足");
        }
        return "OK";
    }
}
