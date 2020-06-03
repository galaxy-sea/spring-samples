package com.galaxy.spring.data.redis.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author galaxy
 */
public class StringRedisClient {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void setString() {
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        BoundHashOperations<Object, Object, Object> boundHashOperations = redisTemplate.boundHashOps("data");


        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();
        BoundListOperations<Object, Object> boundListOperations = redisTemplate.boundListOps("data");


        SetOperations<Object, Object> setOperations = redisTemplate.opsForSet();
        BoundSetOperations<Object, Object> boundSetOperations = redisTemplate.boundSetOps("data");


        setOperations.							scan();
        boundSetOperations.							scan();


    }


}
