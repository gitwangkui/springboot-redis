package redmaple.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: uwank171
 * @date: 2022/8/26 14:25
 */
@Slf4j
@Configuration
public class JedisConfig {

    @Value("${spring.redis.database}")
    private String database;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.sentinel.master}")
    private String master;
    @Value("${spring.redis.sentinel.nodes}")
    private String nodes;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxTotal;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    private static JedisSentinelPool jedisSentinelPool = null;

    public Jedis getJedis() {
        if (jedisSentinelPool == null) {
            jedisSentinelPool();
        }

        if (jedisSentinelPool != null) {
            return jedisSentinelPool.getResource();
        }
        return null;
    }

    public void closeJedis(Jedis jedis) {
        try {
            if (jedis != null ) {
                jedis.close();
            }
        } catch (Exception e) {
            System.out.println("closeJedis Exception ");
            e.printStackTrace();
        }
    }

    @Bean
    public JedisSentinelPool jedisSentinelPool() {
        try {
            Set nodeSet = new HashSet<>();
            //分割出集群节点
            String[] cNodes = nodes.split(",");
            Arrays.stream(cNodes).filter(StringUtils::isNotBlank).forEach(node->{
//                String[] hp = node.split(":");
//                nodeSet.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
                nodeSet.add(node);
            });
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMinIdle(minIdle);
            jedisPoolConfig.setMaxIdle(maxTotal);
            jedisPoolConfig.setMaxTotal(maxTotal);
            jedisPoolConfig.setBlockWhenExhausted(true);
            jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
            jedisPoolConfig.setTestOnBorrow(true);
            jedisPoolConfig.setTestOnReturn(true);
            jedisSentinelPool = new JedisSentinelPool(master, nodeSet, jedisPoolConfig,
                    timeout, password, Integer.valueOf(database));
            return jedisSentinelPool;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedisSentinelPool;
    }

}
