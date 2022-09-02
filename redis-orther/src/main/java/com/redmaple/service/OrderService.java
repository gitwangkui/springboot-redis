package com.redmaple.service;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/2 15:09
 */
public interface OrderService {

    /**
     * 处理过期的key
     * @param expiraKey
     */
    void doOrderExpireKey(String expiraKey);

}
