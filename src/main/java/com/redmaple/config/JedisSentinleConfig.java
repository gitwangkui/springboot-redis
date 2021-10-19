//package com.redmaple.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @description: 该配置也是可以，配置文件 application-0.yml
// * @author: uwank171
// * @date: 2021/10/18 16:58
// */
//@Configuration
//@EnableAutoConfiguration
//public class JedisSentinleConfig {
//
//
//    @Value("#{'${spring.redis.sentinel.nodes}'.split(',')}")
//    private List<String> nodes;
//
//    @Value("${spring.redis.sentinel.master}")
//    private String master;
//
//    @Value("${spring.redis.sentinel.password}")
//    private String password;
//
//    /**
//     *  pool:
//     *         max-active: 256
//     *         max-wait: 30000
//     *         max-idle: 64
//     *         min-idle: 32
//     * @return
//     */
//    @Bean
//    @ConfigurationProperties(prefix="spring.redis")
//    public JedisPoolConfig getRedisConfig(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        return config;
//    }
//    @Bean
//    public RedisSentinelConfiguration sentinelConfiguration(){
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
//        //配置matser的名称
//        redisSentinelConfiguration.master(master);
//        //配置redis的哨兵sentinel
//        Set<RedisNode> redisNodeSet = new HashSet<>();
//        nodes.forEach(x->{
//            redisNodeSet.add(new RedisNode(x.split(":")[0],Integer.parseInt(x.split(":")[1])));
//        });
//        redisSentinelConfiguration.setSentinels(redisNodeSet);
//        redisSentinelConfiguration.setPassword(password);
//        return redisSentinelConfiguration;
//    }
//
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig, RedisSentinelConfiguration sentinelConfig) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(sentinelConfig,jedisPoolConfig);
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
//        // 设置值（value）的序列化采用FastJsonRedisSerializer。
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
////        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
//        // 设置键（key）的序列化采用StringRedisSerializer。
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//}
