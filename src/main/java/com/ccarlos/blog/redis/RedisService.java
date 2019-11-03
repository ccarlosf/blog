package com.ccarlos.blog.redis;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * @description: Redis服务类
 * @author: Created by ccarlos
 * @date: 2019/3/9 23:32
 */
@Slf4j
@Service
public class RedisService {

    //TODO 用法流程 不注入?
    @Autowired
    private JedisPool jedisPool;

    /**
     * @description: 获取单个对象
     * @author: ccarlos
     * @date: 2019/3/9 23:33
     * @param: keyPrefix 键前缀
     * @param: key 键
     * @param: clazz 类
     * @return: T 泛型
     */
    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @description: 设置对象值
     * @author: ccarlos
     * @date: 2019/3/11 21:43
     * @param: keyPrefix 键前缀
     * @param: key 键
     * @param: value 对象值
     * @return: java.lang.Boolean
     */
    //TODO 删除<T>类型?
    public <T> Boolean set(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (StringUtils.isBlank(str)) {
                return false;
            }
            //生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            Integer expireTime = keyPrefix.expireTime();
            log.info("键:{},值:{},过期时间:{}",realKey,str,expireTime);
            if (expireTime <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, expireTime, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @description: 判断key是否存在
     * @author: ccarlos
     * @date: 2019/3/11 21:58
     * @param: keyPrefix 键前缀
     * @param: key 键
     * @return: java.lang.Boolean
     */
    public <T> Boolean exists(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }


    /**
     * @description: 根据key删除缓存值
     * @author: ccarlos
     * @date: 2019/3/28 20:32
     * @param: keyPrefix 键前缀
     * @param: key 键
     * @return: java.lang.Boolean
     */
    public Boolean delete(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            //TODO 类型接收 redis long
            Long ret = jedis.del(realKey);
            return ret > 0;
        } finally {
            returnToPool(jedis);
        }

    }

    /**
     * @description: 增加值
     * @author: ccarlos
     * @date: 2019/3/30 12:35
     * @param: keyPrefix 键前缀
     * @param: key 键
     * @return: java.lang.Long
     */
    //TODO 去掉泛型
    public <T> Long incr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedisPool.getResource();
            //生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @description: 减少值(1)
     * @author: ccarlos
     * @date: 2019/3/30 12:40
     * @param: keyPrefix 键前缀
     * @param: key 键
     * @return: java.lang.Long
     */
    public <T> Long decr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @description: 根据前缀删除缓存值
     * @author: ccarlos
     * @date: 2019/3/30 19:19
     * @param: keyPrefix 键前缀
     * @return: java.lang.Boolean
     */
    public Boolean delete(KeyPrefix keyPrefix) {
        if (keyPrefix == null) {
            return false;
        }
        List<String> keyList = scanKeys(keyPrefix.getPrefix());
        if (CollectionUtils.isEmpty(keyList)) {
            return true;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keyList.toArray(new String[0]));
            return true;
        } catch (Exception e) {
            log.error("根据前缀删除缓存值失败，键前缀:{}，异常原因:{}", keyList, e);
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @description: 根据键前缀搜索key
     * @author: ccarlos
     * @date: 2019/3/30 19:20
     * @param: key 键前缀
     * @return: java.util.List<java.lang.String>
     */
    private List<String> scanKeys(String keyPrefix) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> keyList = Lists.newArrayList();
            String cursor = "0";
            ScanParams scanParams = new ScanParams();
            scanParams.match("*" + keyPrefix + "*");
            scanParams.count(100);
            do {
                ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
                List<String> result = scanResult.getResult();
                if (CollectionUtils.isNotEmpty(result)) {
                    keyList.addAll(result);
                }
                //再处理cursor
                cursor = scanResult.getStringCursor();
            } while (!cursor.equals("0"));
            return keyList;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @description: string字符串转实体
     * @author: ccarlos
     * @date: 2019/3/11 0:45
     * @param: str 字符串
     * @param: clazz 类类型
     * @return: T 泛型
     */
    //TODO 对其他类型等的考虑，如Boolean等。
    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * @description: 实体转string字符串
     * @author: ccarlos
     * @date: 2019/3/11 21:55
     * @param: value 实体对象值
     * @return: java.lang.String
     */
    private static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return value + "";
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return value + "";
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * @description: 关闭jedis对象
     * @author: ccarlos
     * @date: 2019/3/11 0:45
     * @param: jedis jedis客户端对象
     * @return: void 无返回值类型
     */
    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
