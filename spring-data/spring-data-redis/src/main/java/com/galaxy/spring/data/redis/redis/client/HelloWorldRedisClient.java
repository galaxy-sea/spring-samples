package com.galaxy.spring.data.redis.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * @author llwl
 */
@Component
public class HelloWorldRedisClient {

    //@Autowired
    //private ValueOperations<String, String> valueOperations;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public Object helloWorld() {
        ValueOperations<Object, Object> objectObjectValueOperations = redisTemplate.opsForValue();
        objectObjectValueOperations.set("hello", "helloWorld");
        Object hello = objectObjectValueOperations.get("hello");
        return hello;
    }


}
