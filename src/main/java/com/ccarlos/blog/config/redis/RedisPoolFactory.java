package com.ccarlos.blog.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description: Redis连接池类
 * @author: Created by ccarlos
 * @date: 2019/3/9 23:02
 */
@Slf4j
@Configuration
public class RedisPoolFactory {

    @Autowired
    private RedisConfig redisConfig; //连接池配置

    /**
     * @description: 创建JedisPool连接池( 配置通过配置文件 生成配置一个 Bean 连接池对象 )
     * @author: ccarlos
     * @date: 2019/3/9 23:03
     * @return: redis.clients.jedis.JedisPool
     */
    //TODO 不同的构造器

    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        jedisPoolConfig.setTestOnBorrow(redisConfig.getPoolTestOnBorrow());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(),
                redisConfig.getPort(), redisConfig.getTimeout());

     /*   JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout()*1000, redisConfig.getPassword(), 0);*/
        log.info("redis连接池配置信息:{}", jedisPool);
        return jedisPool;
    }

}