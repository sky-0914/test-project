package com.spring.cloud.transaction.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zc
 * @Date: 2019-05-13 16:39
 * @Description:
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "t2_order")
public class Order implements Serializable {
    private static final long serialVersionUID = -2756670286962856202L;
    @Id
    private String id;
    private int userId;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buyTime = new Date();
    private String name;
    private double price;
}
