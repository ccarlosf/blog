package com.ccarlos.blog.util;

import java.util.UUID;

/**
 * @description: 生成UUID工具类
 * @author: Created by ccarlos
 * @date: 2019/3/31 17:35
 */
public class UUIDUtil {

    /**
     * @description: 生成UUID
     * @author: ccarlos
     * @date: 2019/3/31 17:36
     * @return: java.lang.String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
