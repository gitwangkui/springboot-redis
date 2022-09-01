package com.redmaple.common.util;

import redis.clients.jedis.Jedis;

/**
 * @description: 简易版，可以按需求自己修改
 * @author: uwank171
 * @date: 2022/9/1 13:43
 */
public class JedisUtil2 {

    private static String host = "172.28.13.140";
    private static int port = 6379;

    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            jedis = new Jedis(host, port);
            String key = "redis_key1";
            String value = "redis_1001";
            // 存入数据
            jedis.set(key, value);
            // 获取数据
            System.out.println(jedis.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }



    }

}
