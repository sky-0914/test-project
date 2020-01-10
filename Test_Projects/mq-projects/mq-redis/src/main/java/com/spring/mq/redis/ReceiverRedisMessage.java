package com.spring.mq.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author 赵小胖
 * @Date 2019/8/2 17:47
 * @Description:
 */
@Slf4j
@Service
public class ReceiverRedisMessage {


    public void test1Listener(String msg) {
        log.info("[开始消费REDIS消息队列TOPIC_TEST1数据...],消息数据[{}]", msg);
    }

    public void test2Listener(String msg) {
        log.info("[开始消费REDIS消息队列TOPIC_TEST2数据...],消息数据[{}]", msg);
    }

    public void test3Listener(Object msg) {
        log.info("[开始消费REDIS消息队列TOPIC_TEST3数据...],消息数据[{}]", JSON.toJSONString(msg));
    }

    public void test4Listener(Object msg) {
        log.info("[开始消费REDIS消息队列TOPIC_TEST4数据...],消息数据[{}]", JSON.toJSONString(msg));
    }
}
