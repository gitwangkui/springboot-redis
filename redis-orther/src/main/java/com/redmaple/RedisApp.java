package com.redmaple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/2 14:34
 */
@SpringBootApplication
@MapperScan({"com.redmaple.mapper"})
public class RedisApp {
    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class);
    }
}
