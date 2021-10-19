package com.redmaple.config;

import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 获取jedis-pool
 */
@Configuration
public class RedisConfig {

    @Bean(name = "jedis.pool")
    @Autowired
    public JedisSentinelPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                                       @Value("${spring.redis.sentinel.master}") String clusterName,
                                       @Value("${spring.redis.sentinel.nodes}") String sentinelNodes,
                                       @Value("${spring.redis.timeout}") int timeout,
                                       @Value("${spring.redis.password}") String password) {
        System.out.println("缓存服务器的主服务名称：" + clusterName + ", 主从服务ip&port:" + sentinelNodes);
        Assert.isTrue(!StringUtils.isEmpty(clusterName), "主服务名称配置为空");
        Assert.isTrue(!StringUtils.isEmpty(sentinelNodes), "主从服务地址配置为空");

        Set<String> sentinels = new HashSet<>(Arrays.asList(sentinelNodes.split(",")));
        //JedisSentinelPool sentinelJedisPool = new JedisSentinelPool(clusterName, sentinels, config, Protocol.DEFAULT_TIMEOUT, password);
        JedisSentinelPool sentinelJedisPool = new JedisSentinelPool(clusterName, sentinels, config, timeout, password);

        return sentinelJedisPool;
    }

    /**
     * 获取连接池配置参数信息
     * @param maxTotal
     * @param maxIdle
     * @param maxWaitMillis
     * @param testOnBorrow
     * @param testOnReturn
     * @param blockWhenExhausted
     * @param testWhileIdle
     * @param timeBetweenEvictionRunsMillis
     * @param numTestsPerEvictionRun
     * @param minEvictableIdleTimeMillis
     * @return
     */
    @Bean(name = "jedis.pool.config")
    public JedisPoolConfig jedisPoolConfig(@Value("${spring.redis.jedis.pool.maxTotal}") int maxTotal,
                                           @Value("${spring.redis.jedis.pool.maxIdle}") int maxIdle,
                                           @Value("${spring.redis.jedis.pool.maxWaitMillis}") int maxWaitMillis,
                                           @Value("${spring.redis.jedis.pool.testOnBorrow}") boolean testOnBorrow,
                                           @Value("${spring.redis.jedis.pool.testOnReturn}") boolean testOnReturn,
                                           @Value("${spring.redis.jedis.pool.blockWhenExhausted}") boolean blockWhenExhausted,
                                           @Value("${spring.redis.jedis.pool.testWhileIdle}") boolean testWhileIdle,
                                           @Value("${spring.redis.jedis.pool.timeBetweenEvictionRunsMillis}") long timeBetweenEvictionRunsMillis,
                                           @Value("${spring.redis.jedis.pool.numTestsPerEvictionRun}") int numTestsPerEvictionRun,
                                           @Value("${spring.redis.jedis.pool.minEvictableIdleTimeMillis}") long minEvictableIdleTimeMillis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(blockWhenExhausted);
        config.setTestWhileIdle(testWhileIdle);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

        return config;
    }

}