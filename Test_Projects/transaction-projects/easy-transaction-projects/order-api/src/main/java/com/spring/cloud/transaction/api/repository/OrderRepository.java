package com.spring.cloud.transaction.api.repository;

import com.spring.cloud.transaction.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: zc
 * @Date: 2019-05-13 16:49
 * @Description:
 */
public interface OrderRepository extends JpaRepository<Order, String> {
}
