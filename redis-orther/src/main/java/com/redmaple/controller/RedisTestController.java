package com.redmaple.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/2 14:41
 */
@RestController
public class RedisTestController {

    /**
     * 待支付
     */
    private static final Integer ORDER_STAYPAY = 0;
    /**
     * 失效
     */
    private static final Integer ORDER_INVALID = 2;



}
