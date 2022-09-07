package com.redmaple.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import com.redmaple.common.config.JedisConfig;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/8/29 10:18
 */
@Slf4j
@Component
public class JedisUtil {

    @Autowired
    private JedisConfig jedisConfig;

    public Long setnx(String key, String value) {
        return setnx(key, value, null);
    }

    public Long setnx(String key, String value, Integer timeoutSeconds) {
        Jedis jedis = jedisConfig.getJedis();
        try {
            Long setnx = jedis.setnx(key, value);
            if (null != timeoutSeconds) {
                jedis.expire(key, timeoutSeconds);
            }
            return setnx;
        } catch (Exception e) {
            log.error(">>>>> JedisUtils error " + e);
            return null;
        } finally {
            jedisConfig.closeJedis(jedis);
        }
    }

    /**
     * 判断是否存在key
     * @param key
     * @param timeoutSeconds
     * @return
     */
    public boolean expire(String key, int timeoutSeconds) {
        Jedis jedis = jedisConfig.getJedis();
        try {
            jedis.expire(key, timeoutSeconds);
            return true;
        } catch (Exception e) {
            log.error(">>>>> JedisUtils error " + e);
            return false;
        } finally {
            jedisConfig.closeJedis(jedis);
        }
    }

    /**
     * 存储数据
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = jedisConfig.getJedis();
        try {
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error(">>>>> JedisUtils error " + e);
            return null;
        } finally {
            jedisConfig.closeJedis(jedis);
        }
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = jedisConfig.getJedis();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            log.error(">>>>> JedisUtils error " + e);
            return null;
        } finally {
            jedisConfig.closeJedis(jedis);
        }
    }

    /**
     * 删除数据
     * @param key
     * @return
     */
    public Long del(String key) {
        Jedis jedis = jedisConfig.getJedis();
        try {
            return jedis.del(key);
        } catch (Exception e) {
            log.error(">>>>> JedisUtils error " + e);
            return null;
        } finally {
            jedisConfig.closeJedis(jedis);
        }
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = jedisConfig.getJedis();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            log.error(">>>>> JedisUtils error " + e);
            return false;
        } finally {
            jedisConfig.closeJedis(jedis);
        }
    }

}
