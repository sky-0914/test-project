package com.happyloves.zc.service.order.service.order;

import com.happyloves.zc.service.order.dao.mapper.order.OrderMapper;
import com.happyloves.zc.service.order.model.entity.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zc
 * @Date: 2019/9/14 18:29
 * @Description: http://www.imooc.com/article/287865 Intellij IDEA中Mybatis Mapper自动注入警告的6种解决方案
 * @RequiredArgsConstructor： 会生成一个包含常量final，和标识了NotNull的变量 的构造方法。生成的构造方法是private，如何想要对外提供使用可以使用staticName选项生成一个static方法。
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class OrderService {

    private final OrderMapper mapper;

    public List<Order> findAll() {
        return mapper.selectAll();
    }

    public Order findOneById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

//    public Order save(){
//        mapper.insert()
//
//    }

}
