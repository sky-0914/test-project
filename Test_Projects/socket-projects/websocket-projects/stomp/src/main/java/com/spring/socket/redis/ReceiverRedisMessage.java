package com.spring.socket.redis;

import com.spring.socket.stomp.TestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * @Author 赵小胖
 * @Date 2019/8/2 18:14
 * @Description:
 */
@Slf4j
@Service
public class ReceiverRedisMessage {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    public void test1Listener(Object object) throws Exception {
        log.info("[开始消费REDIS消息队列TOPIC_TEST1数据...],消息数据[{}]", object);
        TestVO.TestTopic testTopic = (TestVO.TestTopic) object;
        if ("error".equals(testTopic.getUserToken())) {
            throw new Exception("error");
        }
        if (testTopic.isSendToAll()) {
            simpMessageSendingOperations.convertAndSend(testTopic.getTopic(), testTopic.getMsg());
        } else {
            simpMessageSendingOperations.convertAndSendToUser(testTopic.getUserToken(), testTopic.getTopic(), testTopic.getMsg());
        }
    }
}
