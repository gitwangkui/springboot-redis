package com.redmaple.controller;

import com.redmaple.common.util.JedisUtil;
import com.redmaple.entity.OrderEntity;
import com.redmaple.mapper.OrderMapper;
import com.redmaple.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JedisUtil jedisUtil;

    private final static String ORDER_ = "order_";

    @RequestMapping("/saveOrder")
    public String saveOrder() {
        // 1.生成token
        String orderToken = ORDER_ + UUID.randomUUID().toString();
        String orderId = System.currentTimeMillis() + "";
        //2. 将该token存放到redis中
        jedisUtil.set(orderToken, orderId);
        jedisUtil.expire(orderToken, 10);
        OrderEntity orderEntity = new OrderEntity(null, "C测试监听redis失效过期的key值", orderId, orderToken);
        int result = orderMapper.insertOrder(orderEntity);
        return result > 0 ? "success" : "fail";
    }

}
