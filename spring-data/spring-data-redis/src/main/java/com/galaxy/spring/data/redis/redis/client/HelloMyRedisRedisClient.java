package com.galaxy.spring.data.redis.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author galaxy
 */
@Component
public class HelloMyRedisRedisClient {

    @Autowired
    private RedisTemplate<Object, Object> myRedisTemplate;

    public Object helloRedis() {
        ValueOperations<Object, Object> objectObjectValueOperations = myRedisTemplate.opsForValue();
        objectObjectValueOperations.set("helloRedis", "helloRedis");
        Object hello = objectObjectValueOperations.get("helloRedis");
        return hello;
    }

}
