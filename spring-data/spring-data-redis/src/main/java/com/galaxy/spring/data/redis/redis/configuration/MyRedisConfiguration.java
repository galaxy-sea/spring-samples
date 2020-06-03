package com.galaxy.spring.data.redis.redis.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

/**
 * @author galaxy
 */
@Configuration
public class MyRedisConfiguration {

    @Value("${spring.my-redis.database}")
    private Integer database;
    @Value("${spring.my-redis.host}")
    private String host;
    @Value("${spring.my-redis.port}")
    private Integer port;
    @Value("${spring.my-redis.password}")
    private String password;
    @Value("${spring.my-redis.timeout}")
    private Long timeout;

    @Value("${spring.my-redis.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.my-redis.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.my-redis.pool.max-wait}")
    private Long maxWait;
    @Value("${spring.my-redis.pool.min-idle}")
    private Integer minIdle;


    @Bean
    public RedisTemplate myRedisTemplate(RedisConnectionFactory mytRedisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setExposeConnection(true);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setDefaultSerializer(RedisSerializer.json());
        redisTemplate.setKeySerializer(RedisSerializer.json());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashKeySerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setStringSerializer(RedisSerializer.string());
        //redisTemplate.setScriptExecutor();
        redisTemplate.setEnableTransactionSupport(true);
        //redisTemplate.setBeanClassLoader();
        redisTemplate.setConnectionFactory(mytRedisConnectionFactory);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory mytRedisConnectionFactory(RedisStandaloneConfiguration myRedisStandaloneConfiguration,
                                                             LettuceClientConfiguration myLettuceClientConfiguration) {
        return new LettuceConnectionFactory(myRedisStandaloneConfiguration, myLettuceClientConfiguration);
    }

    @Bean
    public RedisStandaloneConfiguration myRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        return redisStandaloneConfiguration;
    }


    @Bean
    public LettuceClientConfiguration myLettuceClientConfiguration( GenericObjectPoolConfig<Object> myGenericObjectPoolConfig) {
        return LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout)).poolConfig(myGenericObjectPoolConfig).build();
    }

    @Bean
    public GenericObjectPoolConfig<Object> myGenericObjectPoolConfig() {
        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }


}
