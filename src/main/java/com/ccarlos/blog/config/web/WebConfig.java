package com.ccarlos.blog.config.web;

import com.ccarlos.blog.interceptor.HttpInterceptor;
import com.ccarlos.blog.interceptor.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @description: 博客程序配置类
 * @author: Created by ccarlos
 * @date: 2019/3/1 23:25
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserArgumentResolver userArgumentResolver; //用户访问验证

    @Autowired
    private HttpInterceptor httpInterceptor; //http请求拦截器

    @Autowired
    private AccessInterceptor accessInterceptor; //访问拦截器

    /**
     * @description: 拦截器注册
     * @author: ccarlos
     * @date: 2019/3/1 23:22
     * @param: registry 拦截器注册
     * @return: void 无返回值类型
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor);
        registry.addInterceptor(accessInterceptor);
    }

    /**
     * @description: 用户访问校验
     * @author: ccarlos
     * @date: 2019/4/10 11:01
     * @param: argumentResolvers
     * @return: void
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
}
