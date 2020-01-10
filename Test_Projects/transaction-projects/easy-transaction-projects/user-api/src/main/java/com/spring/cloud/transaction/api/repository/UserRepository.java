package com.spring.cloud.transaction.api.repository;

import com.spring.cloud.transaction.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author: zc
 * @Date: 2019-05-13 17:07
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("UPDATE User u SET u.balance=u.balance-:price,u.totalConsumption=u.totalConsumption+:price WHERE u.id=:userId AND u.balance>:price")
    int consumption(@Param("userId") int userId, @Param("price") double price);
}
