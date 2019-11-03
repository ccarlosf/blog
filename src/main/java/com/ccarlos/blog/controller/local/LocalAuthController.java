package com.ccarlos.blog.controller.local;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.service.LocalAuthService;
import com.ccarlos.blog.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @description: 本地账号控制器
 * @author: Created by ccarlos
 * @date: 2019/3/31 15:54
 */
@Slf4j
@RestController
@RequestMapping("/localAuth")
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    /**
     * @description: 本地账号(用户)注册
     * @author: ccarlos
     * @date: 2019/3/31 16:19
     * @param: user
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    @PostMapping("/register")
    public JsonResponse<String> registerLocalAuth(User user) {
        return localAuthService.registerLocalAuth(user);
    }

    /**
     * @description: 用户本地账号登录
     * @author: ccarlos
     * @date: 2019/4/1 19:52
     * @param: loginVo 登录Vo对象
     * @param: response 响应
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    @PostMapping("/login")
    public JsonResponse<String> login(@Valid LoginVo loginVo, HttpServletResponse response) {
        log.info("登录账号信息:{}", loginVo.toString());
        return localAuthService.login(loginVo, response);
    }
}
