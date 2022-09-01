package com.redmaple.controller;

import com.redmaple.common.util.JedisUtil;
import com.redmaple.common.util.JedisUtil3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/1 14:01
 */
@RestController
public class RedisStandloneController {

    @Autowired
    private JedisUtil jedisUtil;

    @GetMapping("/setKey")
    public String setKey(String key, String value) {
        jedisUtil.set(key, value);
        return "===setKey完成===key：" + key + " value：" + value;
    }

    @GetMapping("/getKey")
    public String getKey(String key) {
        String value = jedisUtil.get(key);
        return "===getKey完成===key：" + key + " value：" + value;
    }


//=========================================================================

    @Autowired
    private JedisUtil3 jedisUtil3;

    @GetMapping("/setObj")
    public String setObj(String key, String value) {
        jedisUtil3.setObject(key, value);
        return "===setKey完成===key：" + key + " value：" + value;
    }

    @GetMapping("/getObj")
    public String getObj(String key) {
        String value = (String) jedisUtil3.getObjet(key);
        return "===getKey完成===key：" + key + " value：" + value;
    }
}
