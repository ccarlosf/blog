package com.ccarlos.blog.redis;

/**
 * @description: 访问有效期key
 * @author: Created by ccarlos
 * @date: 2019/4/10 10:05
 */
public class AccessKey extends CommonPrefix {

    /**
     * @description: 私有化构造函数
     * @author: ccarlos
     * @date: 2019/4/10 10:10
     * @param: expireTime 过期时间
     * @param: prefix 前缀
     */
    private AccessKey(Integer expireTime, String prefix) {
        super(expireTime, prefix);
    }

    /**
     * @description: 获取带有效期的访问有效期key
     * @author: ccarlos
     * @date: 2019/4/10 10:13
     * @param: expireTime
     * @return: com.ccarlos.blog.redis.AccessKey
     * @throws:
     */
    public static AccessKey getAccessKeyWithExpireTime(Integer expireTime) {
        return new AccessKey(expireTime,"accessKey");
    }
}
