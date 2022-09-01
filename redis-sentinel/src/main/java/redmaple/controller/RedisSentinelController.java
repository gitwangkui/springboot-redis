package redmaple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redmaple.common.util.JedisUtil;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/1 14:01
 */
@RestController
public class RedisSentinelController {

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

}
