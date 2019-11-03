package com.ccarlos.blog.redis;

/**
 * @description: reids用户对象键
 * @author: Created by ccarlos
 * @date: 2019/4/5 11:05
 */

public class UserKey extends CommonPrefix {

    //token过期时间
    public static final Integer TOKEN_EXPIRE_TIME = 3600;

    /**
     * @description: 构造器
     * @author: ccarlos
2019/4/5d18:3420 * @param: expireTime
 * @param: prefix期时间
     * @param: prefix 键前缀
     */
    private UserKey(Integer expireTime, String prefix) {
        super(expireTime, prefix);
    }

    //用户token键
    public static UserKey userTokenKey = new UserKey(TOKEN_EXPIRE_TIME, "userTokenKey");

    //用户Id键
    public static UserKey userId=new UserKey(0,"userId");
}
