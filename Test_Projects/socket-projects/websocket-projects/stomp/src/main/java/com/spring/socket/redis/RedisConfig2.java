package com.spring.socket.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Author 赵小胖
 * @Date 2019/8/2 18:12
 * @Description:
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig2 extends CachingConfigurerSupport {

    @Value("${sys.dataCaching.expireTime:0}")
    private int expireTime;

    @Value("${spring.redis.host:localhost}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;


    /**
     * 配置单数据库
     */
//    @Resource
//    private LettuceConnectionFactory lettuceConnectionFactory;
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
     * 配置多数据库   @Primary 该注解表示作为主数据库
     *
     * @return
     */
    @Primary
    @Bean("lettuceConnectionFactory0")
    LettuceConnectionFactory lettuceConnectionFactory0() {

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

//        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
//                .poolConfig(poolConfig)
//                .build();

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettuceClientConfigurationBuilder.build());
        return factory;
    }

    @Bean("lettuceConnectionFactory1")
    LettuceConnectionFactory lettuceConnectionFactory1() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(1);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettuceClientConfigurationBuilder.build());
        return factory;
    }

    @Bean("lettuceConnectionFactoryTopic")
    LettuceConnectionFactory lettuceConnectionFactoryTopic() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(2);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettuceClientConfigurationBuilder.build());
        return factory;
    }

    /**
     * 设置序列化
     *
     * @param lettuceConnectionFactory0
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate0(@Qualifier("lettuceConnectionFactory0") LettuceConnectionFactory lettuceConnectionFactory0) {//创建RedisTemplate
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = this.jacksonSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        log.info("默认数据库 [{}]", lettuceConnectionFactory0.getDatabase());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory0);
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

    @Bean
    public RedisTemplate<String, Object> redisTemplate1(@Qualifier("lettuceConnectionFactory1") LettuceConnectionFactory lettuceConnectionFactory1) {//创建RedisTemplate
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = this.jacksonSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        log.info("默认数据库 [{}]", lettuceConnectionFactory1.getDatabase());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory1);
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

    @Bean
    public RedisTemplate<String, Object> redisTemplateTopic(@Qualifier("lettuceConnectionFactoryTopic") LettuceConnectionFactory lettuceConnectionFactoryTopic) {//创建RedisTemplate
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = this.jacksonSerializer();
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        log.info("默认数据库 [{}]", lettuceConnectionFactoryTopic.getDatabase());
        redisTemplate.setConnectionFactory(lettuceConnectionFactoryTopic);
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

    public static final String REDIS_KET_TOPIC = "TOPIC_TEST1";

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param lettuceConnectionFactoryTopic
     * @param test1ListenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(@Qualifier("lettuceConnectionFactoryTopic") LettuceConnectionFactory lettuceConnectionFactoryTopic,
                                            MessageListenerAdapter test1ListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactoryTopic);
        //监听TEST1情况主题并绑定消息订阅处理器
        container.addMessageListener(test1ListenerAdapter, new PatternTopic(REDIS_KET_TOPIC));
//        container.addMessageListener(test2ListenerAdapter, new PatternTopic("test"));
        return container;
    }

    @Bean
    MessageListenerAdapter test1ListenerAdapter(ReceiverRedisMessage receiver) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "test1Listener");
        messageListenerAdapter.setSerializer(this.jacksonSerializer());
        return messageListenerAdapter;
    }

//    @Bean
//    MessageListenerAdapter test2ListenerAdapter(ReceiverRedisMessage receiver) {
//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "test2Listener");
//        messageListenerAdapter.setSerializer(this.jacksonSerializer());
//        return messageListenerAdapter;
//    }

}
