package com.happyloves.zc.service.goods.dao;

import com.happyloves.zc.service.goods.entity.Goods;
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
public interface GoodsJPA extends JpaRepository<Goods, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Goods g SET g.inventory=g.inventory - :count WHERE g.id = :id AND g.inventory >= :count")
    int inventory(@Param("id") int id, @Param("count") int count);
}
