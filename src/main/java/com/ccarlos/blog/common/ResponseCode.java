package com.ccarlos.blog.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 响应状态码枚举类
 * @author: Created by ccarlos
 * @date: 2019/2/28 22:25
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    //成功响应
    SUCCESS(0, "SUCCESS"),

    //错误响应
    ERROR(1, "ERROR"),

    //需要进行登录响应
    NEED_LOGIN(10, "NEED_LOGIN"),

    //系统错误响应
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    //响应状态码
    //TODO final类型?不变
    private int code;

    //响应状态描述
    private String desc;

}
