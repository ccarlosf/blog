package com.ccarlos.blog.verification;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @description: 访问次数限制注解
 * @author: Created by ccarlos
 * @date: 2019/4/9 20:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    //访问时间
    int seconds();

    //最大访问次数
    int maxAccessCount();

    boolean needLogin() default true;
}
