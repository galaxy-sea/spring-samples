package com.galaxy.spring.data.redis;

import com.galaxy.spring.data.redis.redis.client.HelloMyRedisRedisClient;
import com.galaxy.spring.data.redis.redis.client.HelloWorldRedisClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDataRedisApplicationTests {

    @Autowired
    private HelloWorldRedisClient helloWorldRedisClient;

    @Autowired
    private HelloMyRedisRedisClient helloMyRedisRedisClient;

    @Test
    void contextLoads() {
    }


    @Test
    public void HelloWorldTest(){
        Object o = helloWorldRedisClient.helloWorld();
        System.out.println(o);
    }

    @Test
    public void HelloRedisTest(){
        Object o = helloMyRedisRedisClient.helloRedis();
        System.out.println(o);
    }

}
