package com.ccarlos.blog.controller.frontend;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.service.UserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 用户控制器
 * @author: Created by ccarlos
 * @date: 2019/2/25 22:43
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
}
