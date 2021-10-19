package com.redmaple.controller;

import com.redmaple.config.RedisClient;
import com.redmaple.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: uwank171
 * @date: 2021/10/19 09:04
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/setRedisValue", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String setRedisValue(HttpServletRequest request) {
        redisService.set("testKey", "hello redis", 100000);
        return "";
    }

    @RequestMapping(value = "/getRedisValue", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getRedisValue(HttpServletRequest request) {
        Object obj = redisService.get("testKey");
        return obj.toString();
    }

    @GetMapping("/set/{key}/{value}")
    public String set(@PathVariable("key")String key, @PathVariable("value")String value){
        Jedis jedis = redisClient.getJedis();
        jedis.set(key, value);
        redisClient.close(jedis);
        System.out.println(">>>>> 完成 key:" + key +"== value:" + value);
        return ">>>>> 添加完成 key:" + key +"== value:" + value;
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable("key")String key){
        Jedis jedis = redisClient.getJedis();
        String value = jedis.get(key);
        redisClient.close(jedis);
        System.out.println(">>>>> 完成 key:" + key +"== value:" + value);
        return ">>>>> 查询完成 key:" + key +"== value:" + value;
    }

}
