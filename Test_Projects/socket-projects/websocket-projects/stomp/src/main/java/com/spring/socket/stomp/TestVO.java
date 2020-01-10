package com.spring.socket.stomp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zc
 * @Date: 2019-06-17 14:16
 * @Description:
 */
public class TestVO {
    @Data
    public static class TestRequest implements Serializable {
        private static final long serialVersionUID = -4418911448466329145L;
        private String name;
        private int age;
        private String message;
    }

    @Data
    public static class TestResponse implements Serializable {
        private static final long serialVersionUID = -4056269527830061490L;
        private boolean flag;
        private String message;
        private Object data;
    }

    @Data
    public static class TestTopic implements Serializable {
        private static final long serialVersionUID = -5745461114228895249L;
        private String topic;
        private boolean isSendToAll;
        private String userToken;
        private String msg;
    }
}
