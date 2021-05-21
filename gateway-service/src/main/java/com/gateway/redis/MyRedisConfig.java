package com.gateway.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
* @Description 使用@EnableCaching开启缓存 
* @Author ccl
* @CreateDate 2020/11/18 13:14
**/
@Configuration
@EnableCaching
@Slf4j
@SuppressWarnings("all")
public class MyRedisConfig extends CachingConfigurerSupport {
    
    /** 
     * 作用：配置自定义redisTemplate，将传输对象进行序列化
     * redis序列化默认是jdk的序列化，此处使用json序列化
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        /**Json序列化配置*/ 
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setDateFormat(new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss"));
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        /**String序列化配置*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        /**key采用string序列化，value采用json序列化*/
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // redisTemplate.setHashValueSerializer(stringRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 自动开启redis缓存, 使用@Cacheable(value = "user-key")即可，value的值即redis中的key
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for(Object obj : params){
                    sb.append(obj.toString());
                }
                // com.basic.core.UserProviderlogout
                return sb.toString();
            }
        };
    }
    
}
