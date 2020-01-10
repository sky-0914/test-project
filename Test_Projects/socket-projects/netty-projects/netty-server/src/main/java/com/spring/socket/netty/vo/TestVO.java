package com.spring.socket.netty.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zc
 * @Date: 2019-06-20 18:30
 * @Description:
 */
public class TestVO {
    @Data
    public static class TestRequest implements Serializable {

        private static final long serialVersionUID = -8527563006713380517L;
        private String name;
        private int age;
        private boolean flag;
        private Date time;
    }

    @Data
    public static class TestReponse implements Serializable {

        private static final long serialVersionUID = -3164367806605631623L;
        private String name;
        private boolean flag;
    }
}
