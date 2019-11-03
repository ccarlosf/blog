package com.ccarlos.blog.interceptor;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.CodeMessage;
import com.ccarlos.blog.common.constant.RabbitMQConst;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.redis.AccessKey;
import com.ccarlos.blog.redis.RedisService;
import com.ccarlos.blog.service.UserService;
import com.ccarlos.blog.verification.AccessLimit;
import com.ccarlos.blog.verification.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @description: 登录身份拦截器(cookie和token)
 * @author: Created by ccarlos
 * @date: 2019/4/9 19:31
 */
@Slf4j
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserService userService;

	/**
	 * @description: 特定时间内访问频繁检查(防刷)
	 * @author: ccarlos
	 * @date: 2019/4/9 19:40
	 * @param: request 请求
	 * @param: response 响应
	 * @param: handler 处理器
	 * @throws: Exception 异常
	 * @return: boolean {@link boolean}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			User user = getUserByToken(request, response);
			//TODO 是否需要判断user为空，默认不判断
			if (user != null) {
				UserContext.setUserThreadLocal(user);
			}
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			//获取Controller层方法上的注解
			AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
			if (accessLimit == null) {
				return true;
			}
			//作为键
			String requestURI = request.getRequestURI();
			int expireTime = accessLimit.seconds();
			int maxAccessCount = accessLimit.maxAccessCount();
			boolean needLogin = accessLimit.needLogin();
			if (needLogin) {
				if (user == null) {
					render(response, CodeMessage.SESSION_ERROR);
					return false;
				}
				requestURI += "-" + user.getId();
				String checkRequestURI = requestURI + "-" + user.getId();
				log.info(" requestURI :{} ", requestURI);
				log.info("requestURI:{}", checkRequestURI);
			}
			AccessKey accessKey = AccessKey.getAccessKeyWithExpireTime(expireTime);
			Integer count = redisService.get(accessKey, requestURI, Integer.class);
			if (count == null) {
				//记访问次数为1
				redisService.set(accessKey, requestURI, 1);
			} else if (count < maxAccessCount) {
				//增加访问次数，加1
				redisService.incr(accessKey, requestURI);
			} else {
				render(response, CodeMessage.ACCESS_LIMIT_FREQUENTLY);
				return false;
			}
		}
		return true;
	}


	/**
	 * @description: 通过token值获取用户信息
	 * @author: ccarlos
	 * @date: 2019/4/9 20:11
	 * @param: request 请求
	 * @param: response 响应
	 * @return: com.ccarlos.blog.model.User
	 */
	private User getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		String cookieToken = getCookieByName(request, RabbitMQConst.COOKIE_NAME_TOKEN);
		String appToken = request.getParameter(RabbitMQConst.COOKIE_NAME_TOKEN);
		//TODO isBlank还是isEmpty
		if (StringUtils.isBlank(cookieToken) && StringUtils.isBlank(appToken)) {
			return null;
		}
		String token = StringUtils.isBlank(cookieToken) ? appToken : cookieToken;
		//TODO getUserByToken重构
		JsonResponse<User> userByToken = userService.getUserByToken(response, token);
		return userByToken.getData();
	}

	/**
	 * @description: 通过cookieName获取cookie
	 * @author: ccarlos
	 * @date: 2019/4/9 20:03
	 * @param: request 请求
	 * @param: cookieName cookie名
	 * @return: java.lang.String
	 */
	private String getCookieByName(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length <= 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * @description: 输出提示信息
	 * @author: ccarlos
	 * @date: 2019/4/9 20:28
	 * @param: response 响应
	 * @param: codeMessage 响应码与提示信息
	 * @return: void
	 */
	//TODO 异常抛出还是捕获
	private void render(HttpServletResponse response, CodeMessage codeMessage) {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e) {
			log.error("获取输出流发生异常，异常信息:{}，异常描述:{}", e.getMessage(), e.toString());
		}
		String str = JSON.toJSONString(JsonResponse.createByErrorCodeMessage(codeMessage));
		try {
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			log.error("IO输出流，输出或者关闭发生异常，异常信息:{}，异常描述:{}", e.getMessage(), e.toString());
		}
	}

}
