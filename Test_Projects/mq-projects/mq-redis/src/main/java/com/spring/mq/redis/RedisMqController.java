package com.spring.mq.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 赵小胖
 * @Date 2019/8/2 15:47
 * @Description: 生产者消费者
 */
@Slf4j
@RestController
public class RedisMqController {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisMqController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 发布者
     * 发送消息到Redis集合中
     *
     * @param msg 消息内容
     * @return 返回字符串
     */
    @GetMapping("/push")
    public String push(String msg) {
        redisTemplate.opsForList().leftPush(Constant.MQ_KEY_STR, msg);
        TestVO testVO = new TestVO();
        testVO.setName(msg);
        redisTemplate.opsForList().leftPush(Constant.MQ_KEY_VO, testVO);
        return "OK";
    }

    /**
     * 消费者
     * 从Redis队列中取出消息
     *
     * @return 返回字符串
     */
    @GetMapping("/pop")
    public String pop() {
        String str = (String) redisTemplate.opsForList().leftPop(Constant.MQ_KEY_STR);
        System.out.println(str);
        TestVO vo = (TestVO) redisTemplate.opsForList().leftPop(Constant.MQ_KEY_VO);
        System.out.println(JSON.toJSONString(vo));
        return "OK";
    }


    @GetMapping("/sendTopic/{i}")
    public String sendTopic(@PathVariable int i, String msg) {
        TestVO testVO = new TestVO();
        testVO.setName(msg);
        switch (i) {
            case 1:
                log.info(Constant.MQ_TOPIC_TEST1);
                redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST1, msg);
                break;
            case 2:
                log.info(Constant.MQ_TOPIC_TEST2);
                redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST2, msg);
                break;
            case 3:
                log.info(Constant.MQ_TOPIC_TEST3);
                redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST3, testVO);
                break;
            case 4:
                log.info(Constant.MQ_TOPIC_TEST4);
                redisTemplate.convertAndSend(Constant.MQ_TOPIC_TEST4, testVO);
                break;
        }
        return "OK";
    }
}
