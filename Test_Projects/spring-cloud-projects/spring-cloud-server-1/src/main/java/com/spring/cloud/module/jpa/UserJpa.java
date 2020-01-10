package com.spring.cloud.module.jpa;

import com.spring.cloud.module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: 赵小超
 * @Date: 2018/11/1 23:09
 * @Description:
 */
public interface UserJpa extends JpaRepository<User, Integer> {

}
