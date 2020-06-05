package com.galaxy.spring.data.redis.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author galaxy
 */
@Component
public class TransactionsRedisClient {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> myRedisTemplate;

    public void redisTransactions() {
        //execute a transaction
        List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForSet().add("transactions", "transactions-1");
                operations.opsForSet().add("transactions", "transactions-2");
                operations.opsForSet().add("transactions", "transactions-3");
                operations.opsForSet().add("transactions", "transactions-4");

                operations.discard();

                // This will contain the results of all operations in the transaction
                return operations.exec();
            }
        });
        System.out.println("Number of items added to set: " + txResults.get(0));
    }

    @Transactional(rollbackFor = Exception.class)
    public void transactions() {

        // must be performed on thread-bound connection
        myRedisTemplate.opsForValue().set("thing1", "thing2");

        // read operation must be executed on a free (not transaction-aware) connection
        myRedisTemplate.keys("*");

        // returns null as values set within a transaction are not visible
        Object thing1 = myRedisTemplate.opsForValue().get("thing1");
        System.out.println(thing1);
    }


}
