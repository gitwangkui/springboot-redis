package com.redmaple.service;

public interface RedisService {

    boolean expire(String key, long time);

    long getExpire(String key);

    void remove(final String... keys);

    void removePattern(final String pattern);

    void remove(final String key);

    boolean exists(final String key);

    Object get(String key);

    boolean set(String key, Object value);

    boolean set(String key, Object value, long time);

    long incr(String key, long delta) ;

    long decr(String key, long delta);

}




