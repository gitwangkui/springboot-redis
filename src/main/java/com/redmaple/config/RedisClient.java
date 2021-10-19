package com.redmaple.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Optional;

@Component
public class RedisClient {

    @Autowired
    private JedisSentinelPool jedisPool;

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return Boolean
     */
    public String set(final String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key, String.valueOf(value));
        } catch (Exception e) {
            System.err.println("[RedisClient] set e,");
            e.printStackTrace();
            return "";
        } finally {
            close(jedis);
        }
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Optional<String> get(final String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return Optional.ofNullable(jedis.get(key));
        } catch (Exception e) {
            System.err.println("[RedisClient] get exception,");
            e.printStackTrace();
            return Optional.empty();
        } finally {
            close(jedis);
        }
    }

    public void close(Jedis jedis) {
        try {
            if (jedis != null ) {
                jedis.close();
                jedis.quit();
                jedis.disconnect();
            }
        } catch (Exception e) {
            System.out.println("close jedis Exception " + System.currentTimeMillis());
            e.printStackTrace();
        }
    }
}