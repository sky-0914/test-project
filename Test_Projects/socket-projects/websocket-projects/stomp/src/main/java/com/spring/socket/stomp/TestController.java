package com.spring.socket.stomp;

import com.spring.socket.redis.RedisConfig2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author 赵小胖
 * @Date 2019/8/2 14:19
 * @Description:
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @GetMapping("/test")
    public String test(String userName, String msg) {
        simpMessageSendingOperations.convertAndSendToUser(userName, "/receive/toUser", msg);
        return "OK";
    }

    @Resource(name = "redisTemplateTopic")
    private RedisTemplate redisTemplateTopic;

    @GetMapping("/redis/topic")
    public String topic(TestVO.TestTopic testTopic) {
        redisTemplateTopic.convertAndSend(RedisConfig2.REDIS_KET_TOPIC, testTopic);
        return "OK";
    }

    @Resource(name = "redisTemplate0")
    private RedisTemplate redisTemplate0;
    @Resource(name = "redisTemplate1")
    private RedisTemplate redisTemplate1;

    @GetMapping("/test/databases")
    public String databases() {
        ValueOperations valueOperations0 = redisTemplate0.opsForValue();
        valueOperations0.set("aaa", "000");
        valueOperations0.set("database0", "000");
        log.info("database 0 数据  [{},{},{}]", valueOperations0.get("aaa"), valueOperations0.get("database0"), valueOperations0.get("database1"));

        ValueOperations valueOperations1 = redisTemplate1.opsForValue();
        valueOperations1.set("aaa", "111");
        valueOperations1.set("database1", "111");
        log.info("database 1 数据  [{},{},{}]", valueOperations1.get("aaa"), valueOperations1.get("database0"), valueOperations1.get("database1"));

        LettuceConnectionFactory lcf0 = (LettuceConnectionFactory) redisTemplate0.getConnectionFactory();
        LettuceConnectionFactory lcf1 = (LettuceConnectionFactory) redisTemplate1.getConnectionFactory();
        log.info("redisTemplate0 数据库是 [{}], redisTemplate1 数据库是 [{}]", lcf0.getDatabase(), lcf1.getDatabase());
        return redisTemplate0.opsForValue().get("aaa").toString() + redisTemplate1.opsForValue().get("aaa").toString();
    }
}
