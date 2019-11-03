package com.ccarlos.blog.redis;


import lombok.AllArgsConstructor;

/**
 * @description: 基础通用键前缀(抽象类)
 * @author: Created by ccarlos
 * @date: 2019/4/5 11:06
 */
@AllArgsConstructor
public abstract class CommonPrefix implements KeyPrefix {

    //过期时间
    private Integer expireTime;

    //键前缀
    private String prefix;

    /**
     * @description: 一个参数(prefix)的构造函数
     * @author: ccarlos
     * @date: 2019/4/5 11:11
     * @param: prefix 键前缀
     */
    public CommonPrefix(String prefix) {
        //0表示永远有效
        this(0, prefix);
    }

    /**
     * @description: 返回有效期
     * @author: ccarlos
     * @date: 2019/4/5 11:16
     * @return: java.lang.Integer
     */
    @Override
    public Integer expireTime() {
        //默认为0，表示永远有效
        return expireTime;
    }

    /**
     * @description: 生成键前缀
     * @author: ccarlos
     * @date: 2019/4/5 11:17
     * @return: java.lang.String
     */
    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + "_" + prefix;
    }
}
