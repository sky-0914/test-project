package com.spring.cloud.transaction.api.repository;

import com.spring.cloud.transaction.api.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Author: zc
 * @Date: 2019-05-13 16:49
 * @Description:
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer> {

    @Modifying
    @Query("UPDATE Goods g SET g.count=g.count-:count WHERE g.id=:goodsId AND g.count>:count")
    int consumption(@Param("goodsId") int goodsId, @Param("count") int count);
}
