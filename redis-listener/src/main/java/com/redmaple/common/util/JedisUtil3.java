package com.redmaple.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * // 存入对象参数需要序列化
 * @author: uwank171
 * @date: 2022/9/1 13:43
 */
@Component
public class JedisUtil3 {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public Object getObjet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
