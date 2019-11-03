package com.ccarlos.blog.redis;

/**
 * @description: 键前缀
 * @author: Created by ccarlos
 * @date: 2019/3/9 23:39
 */
public interface KeyPrefix {

    /**
     * @description: 获取缓存过期时间
     * @author: ccarlos
     * @date: 2019/3/9 23:41
     * @return: java.lang.Integer
     */
    Integer expireTime();

    /**
     * @description: 获取键前缀
     * @author: ccarlos
     * @date: 2019/3/9 23:41
     * @return: java.lang.String
     */
    String getPrefix();
}
