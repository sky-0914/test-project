package com.spring.mq.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zc
 * @Date: 2019/8/29 16:52
 * @Description:
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisSessionController {

    @GetMapping(value = "/first")
    public Map<String, Object> firstResp(HttpSession session, String name) {
        Map<String, Object> map = new HashMap<>();
        TestVO vo = new TestVO();
        vo.setName(name);
        session.setAttribute("account", vo);
        map.put("sessionId", session.getId());
        return map;
    }

    @GetMapping(value = "/sessions")
    public Object sessions(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", session.getId());
        map.put("message", session.getAttribute("account"));
        return map;
    }
}
