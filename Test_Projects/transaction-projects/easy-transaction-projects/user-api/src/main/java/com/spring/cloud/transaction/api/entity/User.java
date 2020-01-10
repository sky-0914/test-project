package com.spring.cloud.transaction.api.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: zc
 * @Date: 2019-05-13 17:02
 * @Description:
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "t1_user")
public class User implements Serializable {
    private static final long serialVersionUID = 9052444512173406106L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String age;
    private double balance;
    private double totalConsumption;
}
