package com.happyloves.zc.service.account.dao;

import com.happyloves.zc.service.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 23:17
 * @Description:
 */
public interface AccountJPA extends JpaRepository<Account, Integer> {

    /**
     * 扣款
     *
     * @param id
     * @param price
     * @return
     */
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - :price WHERE a.id = :id AND a.balance >= :price")
    int pay(@Param("id") int id, @Param("price") int price);
}
