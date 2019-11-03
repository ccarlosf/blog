package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: 本地账号服务层接口定义
 * @author: Created by ccarlos
 * @date: 2019/3/31 15:55
 */
public interface LocalAuthService {

    /**
     * @description: 本地账号(用户)注册
     * @author: ccarlos
     * @date: 2019/3/31 19:26
     * @param: user 用户实体类
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    JsonResponse<String> registerLocalAuth(User user);

    /**
     * @description: 用户本地账号登录
     * @author: ccarlos
     * @date: 2019/4/1 19:56
     * @param: loginVo 登录Vo对象
     * @param: response
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    JsonResponse<String> login(LoginVo loginVo, HttpServletResponse response);

    /**
     * @description: 将用户登录对象和token，一起存到cookiez中
     * @author: ccarlos
     * @date: 2019/4/5 19:07
      * @param: response
     * @param: user
     * @param: token
     * @return: void
     * @throws:
     */
    void addCookie(HttpServletResponse response, User user, String token);

}
