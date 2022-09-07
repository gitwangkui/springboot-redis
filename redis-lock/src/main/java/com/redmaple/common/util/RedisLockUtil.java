package com.redmaple.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author 若成风
 * @description
 * @date 2022/9/7 23:45
 * @copyright (c) 2022, all rights reserved
 **/
@Component
public class RedisLockUtil {

    @Autowired
    private JedisUtil jedisUtil;

    private static final int setnxSuccss = 1;

    /**
     * 获取锁
     *
     * @param lockKey        定义锁的key
     * @param notLockTimeOut 没有获取锁的超时时间     秒
     * @param lockTimeOut    使用锁的超时时间       秒
     * @return
     */
    public String getLock(String lockKey, int notLockTimeOut, int lockTimeOut) {
        // 获取Redis连接
        // 定义没有获取锁的超时时间
        Long endTimeOut = System.currentTimeMillis() + notLockTimeOut;
        while (System.currentTimeMillis() < endTimeOut) {
            String lockValue = UUID.randomUUID().toString();
            // 如果在多线程情况下谁能够setnx 成功返回0 谁就获取到锁
            if (jedisUtil.setnx(lockKey, lockValue) == setnxSuccss) {
                jedisUtil.expire(lockKey, lockTimeOut);
                return lockValue;
            }
            // 否则情况下 在超时时间内继续循环
        }
        return null;
    }

    /**
     * 释放锁 其实就是将该key删除
     *
     * @return
     */
    public Boolean unLock(String lockKey, String lockValue) {
        // 确定是对应的锁 ，才删除
        if (lockValue.equals(jedisUtil.get(lockKey))) {
            return jedisUtil.del(lockKey) > 0 ? true : false;
        }
        return false;
    }

}
