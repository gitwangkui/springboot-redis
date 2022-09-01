package com.redmaple.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/1 13:17
 */
@Configuration
public class JedisConfig {

    /**配置文件中部分参数没有加载进去*/
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxTotal;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;


    private static JedisPool jedisPool = null;

    public Jedis getJedis() {
        if (jedisPool == null) {
            jedisPool();
        }
        if (jedisPool != null) {
            return jedisPool.getResource();
        }
        return null;
    }

    public void closeJedis(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean("jedisPool")
    public JedisPool jedisPool() {
        if (jedisPool == null) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMinIdle(minIdle);
            jedisPoolConfig.setMaxIdle(maxTotal);
            jedisPoolConfig.setMaxTotal(maxTotal);
            jedisPoolConfig.setBlockWhenExhausted(true);
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
            jedisPoolConfig.setTestOnBorrow(true);
            jedisPoolConfig.setTestOnReturn(true);
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        }
        return jedisPool;
    }

}
