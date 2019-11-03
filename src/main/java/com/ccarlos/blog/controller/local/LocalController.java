package com.ccarlos.blog.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 本地账号视图路由控制器
 * @author: Created by ccarlos
 * @date: 2019/4/18 18:49
 */
@Controller
@RequestMapping("/local")
public class LocalController {

	/**
	 * @description: 本地账号注册视图路由
	 * @author: ccarlos
	 * @date: 2019/4/20 16:18
	 * @return: java.lang.String
	 */
	@GetMapping("/register_view")
	public String registerView() {
		return "/register";
	}

	/**
	 * @description: 本地账号登录视图路由
	 * @author: ccarlos
	 * @date: 2019/4/20 16:18
	 * @return: java.lang.String
	 */
	@GetMapping("/login_view")
	public String loginView() {
		return "/login";
	}

	/**
	 * @description: 测试视图路由
	 * @author: ccarlos
	 * @date: 2019/4/20 16:18
	 * @return: java.lang.String
	 */
	@GetMapping("/test_view")
	public String testView() {
		return "/test";
	}
}
