package com.happyloves.zc.service.goods.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 21:22
 * @Description:
 */
@Entity
@Table(name = "goods")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Goods implements Serializable {
    private static final long serialVersionUID = 6006595825558041844L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 11)
    private String name;
    private int inventory;
    private int price;
}
