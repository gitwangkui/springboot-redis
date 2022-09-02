package com.redmaple.service.impl;

import com.redmaple.entity.OrderEntity;
import com.redmaple.mapper.OrderMapper;
import com.redmaple.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/2 15:10
 */
@Service
public class OrderServiceImpl implements OrderService {
    /**
     * 待支付
     */
    private static final Integer ORDER_STAYPAY = 0;
    /**
     * 失效
     */
    private static final Integer ORDER_INVALID = 2;


    @Autowired
    private OrderMapper orderMapper;
    /**
     * 处理过期的key
     *
     * @param expiraKey
     */
    @Override
    public void doOrderExpireKey(String expiraKey) {
        // 根据key查询 value 如果还还是为待支付状态 将订单改为已经超时~~
        OrderEntity orderNumber = orderMapper.getOrderNumber(expiraKey);
        System.out.println(expiraKey);
        if (orderNumber == null) {
            return;
        }
        if (orderNumber.getOrderStatus().equals(ORDER_STAYPAY)) {
            // 将订单状态改为已经失效
            orderMapper.updateOrderStatus(expiraKey, ORDER_INVALID);
        }
        System.out.println(">>>>> doOrderExpireKey 完成");
    }
}
