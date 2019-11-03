package com.ccarlos.blog.verification;

import com.ccarlos.blog.model.User;

/**
 * @description: 用户本地变量类(线程本地变量类)
 * @author: Created by ccarlos
 * @date: 2019/4/9 19:14
 */
public class UserContext {

    //用户线程本地变量
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();

    /**
     * @description: 获取用户线程本地变量
     * @author: ccarlos
     * @date: 2019/4/9 19:23
     * @return: com.ccarlos.blog.model.User
     */
    //TODO 方法名起名
    public static User getUserThreadLocal() {
        return userThreadLocal.get();
    }

    /**
     * @description: 设置用户线程本地变量
     * @author: ccarlos
     * @date: 2019/4/9 19:23
     * @param: user 用户
     * @return: void
     */
    public static void setUserThreadLocal(User user) {
        userThreadLocal.set(user);
    }
}
