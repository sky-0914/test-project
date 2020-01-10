package com.spring.mq.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @Author: zc
 * @Date: 2018/12/7 14:12
 * @Description: SpringBoot2.0 Redis缓存配置
 * @EnableCaching:开启缓存支持
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${sys.dataCaching.expireTime:0}")
    private int expireTime;

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Override
    @Bean
    public KeyGenerator keyGenerator() {//设置自定义key{ClassName + methodName + params}
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append(method.getName());
            sb.append(",Params[");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

    /**
     * 设置Redis缓存过期时间
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //设置缓存过期时间
        if (expireTime > 0) {
            log.info("Redis 缓存过期时间 : {}", expireTime);
            //设置缓存有效期 秒
            redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireTime));
        } else {
            log.info("Redis 未设置缓存过期时间");
        }
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 设置序列化
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {//创建RedisTemplate
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = this.jacksonSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 序列化
     *
     * @return
     */
    private Jackson2JsonRedisSerializer jacksonSerializer() {
        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * =================================================
     */


    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param connectionFactory
     * @param test1ListenerAdapter
     * @param test2ListenerAdapter
     * @param test3ListenerAdapter
     * @param test4ListenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter test1ListenerAdapter,
                                            MessageListenerAdapter test2ListenerAdapter,
                                            MessageListenerAdapter test3ListenerAdapter,
                                            MessageListenerAdapter test4ListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //监听TEST1情况主题并绑定消息订阅处理器
        container.addMessageListener(test1ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST1));
        //监听TEST2主题并绑定消息订阅处理器
        container.addMessageListener(test2ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST2));
        //监听TEST3主题并绑定消息订阅处理器
        container.addMessageListener(test3ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST3));
        //监听TEST4主题并绑定消息订阅处理器
        container.addMessageListener(test4ListenerAdapter, new PatternTopic(Constant.MQ_TOPIC_TEST4));
        return container;
    }

    @Bean
    MessageListenerAdapter test1ListenerAdapter(ReceiverRedisMessage receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "test1Listener");
        messageListenerAdapter.setSerializer(this.jacksonSerializer());
        return messageListenerAdapter;
    }

    @Bean
    MessageListenerAdapter test2ListenerAdapter(ReceiverRedisMessage receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "test2Listener");
        return messageListenerAdapter;
    }

    @Bean
    MessageListenerAdapter test3ListenerAdapter(ReceiverRedisMessage receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "test3Listener");
        return messageListenerAdapter;
    }

    @Bean
    MessageListenerAdapter test4ListenerAdapter(ReceiverRedisMessage receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "test4Listener");
        return messageListenerAdapter;
    }
}
