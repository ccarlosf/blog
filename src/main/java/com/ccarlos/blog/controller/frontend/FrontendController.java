package com.ccarlos.blog.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 用户端视图路由控制器
 * @author: ccarlos
 * @date: 2019/4/20 16:16
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

	/**
	 * @description: 用户端首页路由视图
	 * @author: ccarlos
	 * @date: 2019/4/20 16:18
	 * @return: java.lang.String
	 */
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
