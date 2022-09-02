package com.redmaple.common.listener;

import com.redmaple.entity.OrderEntity;
import com.redmaple.mapper.OrderMapper;
import com.redmaple.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/9/2 14:40
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final static String ORDER_ = "order_";

    @Autowired
    private OrderService orderService;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    /**
     * Redis失效事件 key
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 表示失效过期的key值
        String expiraKey = message.toString();

        // 做业务处理 TODO
        if (expiraKey.startsWith(ORDER_)) {
            orderService.doOrderExpireKey(expiraKey);
        }

    }

}
