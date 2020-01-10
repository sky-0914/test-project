package com.spring.cloud.module.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: 赵小超
 * @Date: 2018/11/1 23:02
 * @Description:
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 4869095882325693818L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String userName;

    private int age;

    private String sex;
}
