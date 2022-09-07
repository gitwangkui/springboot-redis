package com.redmaple.service;

import com.redmaple.common.util.RedisLockUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {

    @Autowired
    private RedisLockUtil redisLockUtil;

    private String lockKey = "mayikt_lock";

    public void service() {

        // 1.获取锁
        String lockValue = redisLockUtil.getLock(lockKey, 5, 5);
        if (StringUtils.isEmpty(lockValue)) {
            System.out.println(Thread.currentThread().getName() + ",获取锁失败!");
            return;
        }
        // 2.获取锁成功执行业务逻辑
        System.out.println(Thread.currentThread().getName() + ",获取成功，lockValue：" + lockValue);
//        // 3.释放lock锁
        redisLockUtil.unLock(lockKey, lockValue);
    }
}
