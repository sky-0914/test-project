package com.spring.cloud.transaction.api;

import com.spring.cloud.transaction.api.repository.UserRepository;
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
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "all")
    public Object getAll() {
        return repository.findAll();
    }

    @Transactional
    @PutMapping(value = "/consumption/{userId}")
    public Object consumption(@PathVariable int userId, double price) throws Exception {
        int i = repository.consumption(userId, price);
        if (i != 1) {
            log.error("余额不足");
            throw new Exception("余额不足");
        }

        return "OK";
    }
}
