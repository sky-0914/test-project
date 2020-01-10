package com.spring.cloud.transaction.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: zc
 * @Date: 2019-05-13 16:39
 * @Description:
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "t3_goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = -2756670286962856202L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int count;

    private double price;
}
