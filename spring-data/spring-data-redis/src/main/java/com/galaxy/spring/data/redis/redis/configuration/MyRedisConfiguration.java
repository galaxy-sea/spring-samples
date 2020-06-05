package com.galaxy.spring.data.redis.redis.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

/**
 * @author galaxy
 */
@Configuration
// 配置 Spring Context 以启用声明性 transaction management
@EnableTransactionManagement
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

    /**
     * RedisTemplate 配置
     *
     * @return RedisTemplate
     */
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
        redisTemplate.setConnectionFactory(mytRedisConnectionFactory());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /**
     * 链接工厂
     *
     * @return RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory mytRedisConnectionFactory() {
        return new LettuceConnectionFactory(myRedisStandaloneConfiguration(), myLettuceClientConfiguration());
    }

    /**
     * redis客户端独立配置信息
     *
     * @return RedisStandaloneConfiguration
     */
    @Bean
    public RedisStandaloneConfiguration myRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        return redisStandaloneConfiguration;
    }

    /**
     * Lettuce 配置
     *
     * @return Lettuce
     */
    @Bean
    public LettuceClientConfiguration myLettuceClientConfiguration() {
        return LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout)).poolConfig(myGenericObjectPoolConfig()).build();
    }

    /**
     * 线程池配置
     *
     * @return GenericObjectPoolConfig 线程池
     */
    @Bean
    public GenericObjectPoolConfig<Object> myGenericObjectPoolConfig() {
        GenericObjectPoolConfig<Object> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        return DataSourceBuilder.create().build();
    }


}
