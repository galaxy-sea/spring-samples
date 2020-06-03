package com.galaxy.spring.data.redis.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author llwl
 */
@Component("keyRedisClient")
public class KeyRedisClient {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void key(){
        redisTemplate.keys()
    }

}
