package com.ccarlos.blog.config.web;

import com.ccarlos.blog.model.User;
import com.ccarlos.blog.verification.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @description: 校验token与cookie类，获取用户信息
 * @author: Created by ccarlos
 * @date: 2019/4/5 20:24
 */
//@Service
@Slf4j
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * @description: 判断是否支持校验，controller层中的loginVo user参数(有/无)
     * @author: ccarlos
     * @date: 2019/4/5 20:32
     * @param: methodParameter controller层中的user参数(有/无)
     * @return: boolean 返回值true/false
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        String methodName = methodParameter.getMethod().getName();
        if (methodName.equals("registerLocalAuth") || methodName.equals("updateUser")||methodName.equals("modifyProfile")) {
            log.info("注册用户无需从UserContext获取用户信息");
            return false;
        }
        log.info("methodParameter:{},supportsParameter是否支持:{}", methodParameter, clazz == User.class);
        return clazz == User.class;
    }

    /**
     * @description: controller层中传入了user对象，判断是否能够从ThreadLocal对象中返回user对象
     * @author: ccarlos
     * @date: 2019/4/5 20:34
     * @param: methodParameter
     * @param: modelAndViewContainer
     * @param: nativeWebRequest
     * @param: webDataBinderFactory
     * @return: java.lang.Object
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return UserContext.getUserThreadLocal();
    }
}
