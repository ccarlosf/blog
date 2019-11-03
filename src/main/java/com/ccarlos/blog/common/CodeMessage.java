package com.ccarlos.blog.common;

import lombok.*;

/**
 * @description: 响应转态码以及对应的描述
 * @author: Created by ccarlos
 * @date: 2019/4/2 11:33
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CodeMessage {

    //TODO int/Integer

    //响应状态码
    private int code;

    //响应描述信息
    private String message;

    //通用模块的响应码与提示信息
    public static CodeMessage SUCCESS = new CodeMessage(0, "success");
    public static CodeMessage SERVER_ERROR = new CodeMessage(5001, "服务端异常");
    public static CodeMessage BIND_ERROR = new CodeMessage(5002, "参数校验异常：%s");
    public static CodeMessage REQUEST_ILLEGAL = new CodeMessage(5003, "请求非法");
    public static CodeMessage ACCESS_LIMIT_FREQUENTLY = new CodeMessage(5004, "访问太频繁！");

    //注册、登录模块的响应码与提示信息
    public static CodeMessage LOGIN_NULL=new CodeMessage(6001,"登录信息不能为空");
    public static CodeMessage USERNAME_EMAIL_BLANK=new CodeMessage(6002,"用户名/邮箱不能同时为空");
    public static CodeMessage USERNAME_BLANK = new CodeMessage(6003, "用户名不能为空");
    public static CodeMessage EMAIL_BLANK = new CodeMessage(6004, "邮箱不能为空");
    public static CodeMessage USERNAME_EMAIL_NOT_EXIST=new CodeMessage(6005,"用户名或者邮箱不存在");
    public static CodeMessage USERNAME_NOT_EXIST=new CodeMessage(6006,"用户名不存在");
    public static CodeMessage EMAIL_NOT_EXIST=new CodeMessage(6007,"邮箱不存在");
    public static CodeMessage PASSWORD_BLANK = new CodeMessage(6008, "登录密码不能为空");
    public static CodeMessage PASSWORD_ERROR = new CodeMessage(6009, "密码错误");
    public static CodeMessage EMAIL_ERROR = new CodeMessage(6010, "邮箱格式错误");
    public static CodeMessage SESSION_ERROR = new CodeMessage(6011, "Session不存在或者已经失效");

    //分类模块的响应码与提示信息
    public static CodeMessage CATEGORY_USER_EXIST=new CodeMessage(7001,"同一用户，该分类已经存在");
    public static CodeMessage CATEGORY_ID_NULL=new CodeMessage(7002,"分类id为空");

    //博客模块的响应码与提示信息
    public static CodeMessage BLOG_ID_NULL=new CodeMessage(8001,"博客id为空");

    //TODO 私有化构造函数，并且使得所有set 方法失效，接口健壮性
    /**
     * @description: 填充参数数据
     * @author: ccarlos
     * @date: 2019/4/3 11:42
     * @param: parameters 参数数据
     * @return: com.ccarlos.blog.common.CodeMessage
     */
    //利用可变长参数定义 ：适用于参数类型可知，但是个数未知的情况
    public CodeMessage fillParameters(Object... parameters) {
        int code = this.code;
        //TODO 格式化
        String message = String.format(this.message, parameters);
        return new CodeMessage(code, message);
    }

}
