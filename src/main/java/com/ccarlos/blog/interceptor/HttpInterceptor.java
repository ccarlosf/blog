package com.ccarlos.blog.interceptor;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: Http请求拦截器
 * @author: Created by ccarlos
 * @date: 2019/3/1 22:57
 */
@Component
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

	private String START_TIME = "requestStartTime";

	/**
	 * @description: 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理
	 * @author: ccarlos
	 * @date: 2019/3/1 22:59
	 * @param: request 请求
	 * @param: response 响应
	 * @param: handler 处理器
	 * @return: boolean {@link boolean}
	 * @throws: Exception 异常
	 */
	//TODO handler的意思
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI().toString();
		//TODO 使用map类型?
		Map<String, String[]> parameterMap = request.getParameterMap();
		//TODO fastjson用法 JsonMapper ObjectMapper的使用
		log.info("请求开始：url地址:{},参数params:{}", url, JSON.toJSONString(parameterMap));
		long startTime = System.currentTimeMillis();
		request.setAttribute(START_TIME, startTime);
		return true;
//      return super.preHandle(request, response, handler);
	}

	/**
	 * @description: 在业务处理器处理请求执行完成后，生成视图之前执行，
	 * 之后处理（调用了Service并返回ModelAndView，但未进行页面渲染），
	 * 有机会修改ModelAndView。
	 * @author: ccarlos
	 * @date: 2019/3/1 23:15
	 * @param: request 请求
	 * @param: response 响应
	 * @param: handler 处理器
	 * @param: modelAndView 模型和视图
	 * @return: void 无返回值类型
	 * @throws: Exception 异常
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//TODO
//        super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * @description: 在DispatcherServlet完全处理完请求后被调用，
	 * 可用于清理资源等。返回处理（已经渲染了页面），
	 * 可以根据ex是否为null判断是否发生了异常，进行日志记录。
	 * @author: ccarlos
	 * @date: 2019/3/1 23:21
	 * @param: request 请求
	 * @param: response 响应
	 * @param: handler 处理器
	 * @param: ex 异常
	 * @return: void 无返回值类型
	 * @throws: Exception 异常
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		String url = request.getRequestURI().toString();
		//类型转换
		long startTime = (Long) request.getAttribute(START_TIME);
		long endTime = System.currentTimeMillis();
		log.info("请求结束，url地址:{},耗时cost:{}", url, endTime - startTime);
	}
}
