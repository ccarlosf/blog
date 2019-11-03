package com.ccarlos.blog.service.impl;


import com.ccarlos.blog.common.constant.RabbitMQConst;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.common.CodeMessage;
import com.ccarlos.blog.dao.UserMapper;
import com.ccarlos.blog.exception.GlobalException;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.redis.RedisService;
import com.ccarlos.blog.redis.UserKey;
import com.ccarlos.blog.service.LocalAuthService;
import com.ccarlos.blog.util.MD5Util;
import com.ccarlos.blog.util.UUIDUtil;
import com.ccarlos.blog.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 本地账号服务层接口实现
 * @author: Created by ccarlos
 * @date: 2019/3/31 15:56
 */
@Slf4j
@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserMapper userMapper;

    /**
     * @description: 本地账号(用户)注册
     * @author: ccarlos
     * @date: 2019/3/31 16:32
     * @param: user
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    @Override
    @Transactional
    public JsonResponse<String> registerLocalAuth(User user) {
        //校验用户名是否重复
        JsonResponse valiadResponse = this.checkValid(RabbitMQConst.USERNAME, user.getUsername());
        //校验不成功，直接返回提示信息
        if (!valiadResponse.isSuccess()) {
            return valiadResponse;
        }

        //校验邮箱是否重复
        valiadResponse = this.checkValid(RabbitMQConst.EMAIL, user.getEmail());
        //校验不成功，直接返回提示信息
        if (!valiadResponse.isSuccess()) {
            return valiadResponse;
        }
        user.setRole(RabbitMQConst.Role.ROLE_CUSTOMER);
        //盐值
        String salt = UUIDUtil.getUUID();
        user.setSalt(salt);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword() + salt));
        int resultCount = userMapper.insertUser(user);
        if (resultCount == 0) {
            return JsonResponse.createByErrorMessage("注册失败");
        }
        return JsonResponse.createBySuccessMessage("注册成功");
    }

    /**
     * @description: 用户本地账号登录
     * @author: ccarlos
     * @date: 2019/4/1 19:57
     * @param: loginVo 登录Vo对象
     * @param: response 响应
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    @Override
    public JsonResponse<String> login(LoginVo loginVo, HttpServletResponse response) {
        //TODO 为空的情况 JsonResponse.createBySuccessMessage(token);
        if (loginVo == null) {
            throw new GlobalException(CodeMessage.LOGIN_NULL);
        }
        String userName = loginVo.getUsername();
        String email = loginVo.getEmail();
        //TODO jsr3 去除这部分代码
        //用户名(邮箱)不能同时为空
        if (StringUtils.isBlank(userName) && StringUtils.isBlank(email)) {
            throw new GlobalException(CodeMessage.USERNAME_EMAIL_BLANK);
        }

        //判断用户名(邮箱)是否存在
        if (StringUtils.isNotBlank(userName)) {
            //TODO 校验的改造 代码去除 selectByUsernameOrEmail重复
            JsonResponse valiadResponse = this.checkValid(RabbitMQConst.USERNAME, userName);
            if (valiadResponse.isSuccess()) {
                throw new GlobalException(CodeMessage.USERNAME_NOT_EXIST);
            }
        } else {
            JsonResponse valiadResponse = this.checkValid(RabbitMQConst.EMAIL, userName);
            if (valiadResponse.isSuccess()) {
                throw new GlobalException(CodeMessage.EMAIL_NOT_EXIST);
            }
        }

        //TODO 逻辑与上面的代码有重复
        User user = userMapper.selectByUsernameOrEmail(userName, email);
        if (user == null) {
            //用户名或者邮箱不存在
            throw new GlobalException(CodeMessage.USERNAME_EMAIL_NOT_EXIST);
        }
        String salt = user.getSalt();
        String md5Password = MD5Util.MD5EncodeUtf8(loginVo.getPassword() + salt);
//        user = userMapper.selectByUsernameAndPassword(userName, md5Password);
        if (!md5Password.equals(user.getPassword())) {
            throw new GlobalException(CodeMessage.PASSWORD_ERROR);
        }
        //生成token与cookie
        String token = UUIDUtil.getUUID();
        addCookie(response, user, token);

        return JsonResponse.createBySuccess("登录成功", token);
    }

    /**
     * @description: 将用户登录对象和token，一起存到cookiez中
     * @author: ccarlos
     * @date: 2019/4/5 10:49
     * @param: response 响应对象
     * @param: user 用户实体
     * @param: token token值
     * @return: void
     */
    public void addCookie(HttpServletResponse response, User user, String token) {
        redisService.set(UserKey.userTokenKey, token, user);
        Cookie cookie = new Cookie(RabbitMQConst.COOKIE_NAME_TOKEN, token);
        //cookie有效期==缓存有效期
        cookie.setMaxAge(UserKey.userTokenKey.expireTime());
        log.info("cookie，键:{}，值:{}，过期时间:{}", RabbitMQConst.COOKIE_NAME_TOKEN, token, UserKey.userTokenKey.expireTime());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * @description: 校验用户名或者邮箱是否存在
     * @author: ccarlos
     * @date: 2019/3/31 16:46
     * @param: type 类型(用户名类型或者邮箱类型)
     * @param: str 用户名或者邮箱
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    //TODO 1.校验的改造，注册/登录 通用  2.使用jsr3简化用户名和邮箱为空的判断以及邮箱格式的判断
    private JsonResponse<String> checkValid(String type, String str) {
        if (StringUtils.isNotBlank(type)) {
            if (RabbitMQConst.USERNAME.equals(type)) {
                if (StringUtils.isBlank(str)) {
                    return JsonResponse.createByErrorCodeMessage(CodeMessage.USERNAME_BLANK);
                }
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return JsonResponse.createByErrorMessage("用户名已经存在");
                }
            }
            if (RabbitMQConst.EMAIL.equals(type)) {
                if (StringUtils.isBlank(str)) {
                    return JsonResponse.createByErrorCodeMessage(CodeMessage.EMAIL_BLANK);
                }
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return JsonResponse.createByErrorMessage("邮箱已经存在");
                }
            }
        } else {
            return JsonResponse.createByErrorMessage("校验参数错误");
        }
        return JsonResponse.createBySuccessMessage("校验成功");
    }

}
