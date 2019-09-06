package com.happyloves.zc.service.account.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 赵小胖
 * @Date 2019/9/5 21:22
 * @Description:
 */
@Entity
@Table(name = "account")
@Data
public class Account implements Serializable {
    private static final long serialVersionUID = -8284769269367428759L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 5)
    private String name;
    private int age;
    private int balance;
}
