package com.ccarlos.blog.controller.frontend;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dao.UserMapper;
import com.ccarlos.blog.dto.CommentDTO;
import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.Category;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.redis.RedisService;
import com.ccarlos.blog.service.BlogService;
import com.ccarlos.blog.service.CategoryService;
import com.ccarlos.blog.service.CommentService;
import com.ccarlos.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @description: 用户主页空间控制器
 * @author: ccarlos
 * @date: 2019/5/16 20:13
 */
@Slf4j
@Controller
@RequestMapping("/u")
public class UserspaceController {

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CommentService commentService;

	/**
	 * @description: 获取用户个人信息，并跳转到个人主页空间
	 * @author: ccarlos
	 * @date: 2019/5/16 20:24
	 * @param: username 用户名
	 * @param: model 用户对象
	 * @return: org.springframework.web.servlet.ModelAndView
	 */
	@GetMapping("/profile")
	public ModelAndView profile(Model model, User user) {
		log.info("redis中存储的用户个人信息为:{}", JSON.toJSONString(user));
		model.addAttribute("user", user);
		return new ModelAndView("/userspace/profile", "userModel", model);
	}

	/**
	 * @description: 获取编辑头像的界面
	 * @author: ccarlos
	 * @date: 2019/5/17 10:58
	 * @return: java.lang.String
	 */
	@GetMapping("/avatar")
	public String getAvatar() {
		return "/userspace/avatar";
	}

	/**
	 * @description: 修改用户头像地址
	 * @author: ccarlos
	 * @date: 2019/5/27 10:50
	 * @param: user 用户信息
	 * @return: com.ccarlos.blog.common.JsonResponse
	 */
	@PostMapping("/avatar")
	@ResponseBody
	public JsonResponse<String> modifyAvatar(@RequestBody User user) {
		return userService.modifyAvatar(user);
	}

	/**
	 * @description: 修改个人设置
	 * @author: ccarlos
	 * @date: 2019/5/27 15:06
	 * @param: username 用户名
	 * @param: user 用户对象
	 * @return: java.lang.String
	 */
	@PostMapping("/{username}/profile")
	public String modifyProfile(@PathVariable("username") String username, User user) {
		log.info("用户名为:{}，进行个人设置修改", username);
		userService.modifyProfile(user);
		return "redirect:/u/newProfile";
	}

	/**
	 * @description: 修改个人设置后，刷新个人信息
	 * @author: ccarlos
	 * @date: 2019/5/27 15:43
	 * @param: model 模型
	 * @param: user 用户对象
	 * @return: org.springframework.web.servlet.ModelAndView
	 * @throws:
	 */
	@GetMapping("/newProfile")
	public ModelAndView newProfile(Model model, User user) {
		JsonResponse<User> userResponse = userService.getUserById(user.getId());
		log.info("修改个人设置后，新的个人信息:{}", JSON.toJSONString(userResponse.getData()));
		User existUser = userMapper.selectUserById(user.getId());
		userResponse.getData().setPassword(existUser.getPassword());
		model.addAttribute("user", userResponse.getData());
		return new ModelAndView("/userspace/profile", "userModel", model);
	}

	/**
	 * @description: 用户博客个人空间跳转
	 * @author: ccarlos
	 * @date: 2019/5/28 11:54
	 * @param: username 用户名
	 * @param: model 模型
	 * @param: user 用户对象
	 * @return: java.lang.String
	 */
	@GetMapping("/{username}")
	public String userSpace(@PathVariable("username") String username, Model model, User user) {
		log.info("获取用户名为:{}，的个人主页数据", username);
		model.addAttribute("user", user);
		return "redirect:/u/" + username + "/blog_list";
	}

	/**
	 * @description: 根据用户Id搜索博客列表
	 * @author: ccarlos
	 * @date: 2019/5/28 17:38
	 * @param: username 用户名
	 * @param: order
	 * @param: categoryId
	 * @param: keyword
	 * @param: async
	 * @param: pageIndex
	 * @param: pageSize
	 * @param: model
	 * @param: user
	 * @return: java.lang.String
	 * @throws:
	 */
	@GetMapping("/{username}/blog_list")
	public String getBlogListByUserId(@PathVariable("username") String username,
	                                  @RequestParam(value = "orderBy", required = false, defaultValue = "new") String orderBy,
	                                  @RequestParam(value = "category", required = false) Long categoryId,
	                                  @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	                                  @RequestParam(value = "async", required = false) boolean async,
	                                  @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
	                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
	                                  Model model, User user) {

//		User  user = (User)userDetailsService.loadUserByUsername(username);
		log.info("用户名:{}查询博客列表", username);
		Page<Blog> page = null;

		if (categoryId != null && categoryId > 0) {
			// 分类查询
		} else if (orderBy.equals("hot")) { // 最热查询
			Sort sort = new Sort(Sort.Direction.DESC, "readCount", "commentCount", "voteCount");
			Pageable pageable = new PageRequest(pageIndex, pageSize, sort);
			page = blogService.getHottestBlogListByUserIdAndTitle(pageable, Long.valueOf(user.getId()), keyword);
		} else if (orderBy.equals("new")) { // 最新查询
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			page = blogService.getNewestBlogListByUserIdAndTitle(pageable, Long.valueOf(user.getId()), keyword);
		}

		List<Blog> list = page.getContent();    // 当前所在页面数据列表
		model.addAttribute("user", user);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("blogList", list);
		//TODO 啥意思
		return (async == true ? "/userspace/u :: #mainContainerRepleace" : "/userspace/u");
	}

	/**
	 * @description: 获取新增博客的界面
	 * @author: ccarlos
	 * @date: 2019/7/2 20:07
	 * @param: model 模型
	 * @param: user 用户对象
	 * @return: org.springframework.web.servlet.ModelAndView
	 */
	@GetMapping("/blogs/edit")
	public ModelAndView createBlog(Model model, User user) {
//		User user = (User)userDetailsService.loadUserByUsername(username);
		JsonResponse<List<Category>> categoryResponse = categoryService.getCategoryList(Long.valueOf(user.getId()));
		model.addAttribute("blog", new BlogWithBLOBs());
		model.addAttribute("categoryList", categoryResponse.getData());
		return new ModelAndView("/userspace/blog_edit", "blogModel", model);
	}


	/**
	 * @description: 发布博客
	 * @author: ccarlos
	 * @date: 2019/6/2 12:38
	 * @param: username 用户名
	 * @param: blog 博文
	 * @return: ResponseEntity<Response>
	 * @throws:
	 */
	@PostMapping("/blogs/edit")
	@ResponseBody
	public JsonResponse<String> addBlog(@RequestBody BlogWithBLOBs blogWithBLOBs, User user) {
		log.info("接收博客数据:{}", JSON.toJSONString(blogWithBLOBs));
		// 对 categoryId 进行空处理
		if (blogWithBLOBs.getCategoryId() == null) {
			return JsonResponse.createByErrorMessage("分类不能为空");
		}

		blogWithBLOBs.setUserId(Long.valueOf(user.getId()));
		// 判断是新增还是修改
		//新增博客
		if (blogWithBLOBs.getId() == null) {
			JsonResponse<String> addBlogResponse = blogService.addBlog(blogWithBLOBs);
			if (!addBlogResponse.isSuccess()) {
				return addBlogResponse;
			}
		} else { //修改博客
			JsonResponse<String> modifyBlogResponse = blogService.modifyBlog(blogWithBLOBs);
			if(!modifyBlogResponse.isSuccess()){
				return modifyBlogResponse;
			}
		}

		String redirectUrl = "/u/blogs/" + blogWithBLOBs.getId();
		return JsonResponse.createBySuccess("处理成功", redirectUrl);

	}


	@GetMapping("/blogs/{id}")
	@Transactional
	public String getBlogById(@PathVariable("id") Long id, Model model, User user) {
		JsonResponse blogResponse = blogService.getBlogById(id);
		BlogWithBLOBs blogWithBLOBs = (BlogWithBLOBs) blogResponse.getData();
		// 每次读取，认为阅读量加1
		JsonResponse<String> readCountResponse = blogService.increaseReadCount(id);
		if (!readCountResponse.isSuccess()) {
			log.info("博客id:{}更新博客阅读量失败", id);
		}
		JsonResponse categoryResponse = categoryService.getCategoryById(blogWithBLOBs.getCategoryId());
		JsonResponse<List<CommentDTO>> commentDTOResponse = commentService.getCommentList(id);


		//TODO user密码置空
		user.setPassword(StringUtils.EMPTY);
		model.addAttribute("blogModel", blogWithBLOBs);
		model.addAttribute("userModel", user);
		model.addAttribute("categoryModel", categoryResponse.getData());
		model.addAttribute("comments", commentDTOResponse.getData());
//		model.addAttribute("currentVote",currentVote);
		return "/userspace/blog";
	}

	/**
	 * @description: 根据博客id删除博客
	 * @author: ccarlos
	 * @date: 2019/7/3 10:54
	 * @param: id 博客id
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@DeleteMapping("/{username}/blogs/{id}")
	@ResponseBody
	public JsonResponse<String> removeBlog(@PathVariable("id") Long id, @PathVariable("username") String username) {
		JsonResponse<String> blogResponse = blogService.removeBlog(id);
		if (blogResponse.isSuccess()) {
			String redirectUrl = "/u/" + username + "/blog_list";
			return JsonResponse.createBySuccess("成功删除博客", redirectUrl);
		}
		return blogResponse;
	}

	/**
	 * @description: 根据博客id获取博客修改页面
	 * @author: ccarlos
	 * @date: 2019/7/4 10:35
	 * @param: id 博客id
	 * @param: username 用户名
	 * @param: model 模型
	 * @param: user 用户对象
	 * @return: org.springframework.web.servlet.ModelAndView
	 */
	@GetMapping("/{username}/blogs/edit/{id}")
	public ModelAndView editBlog(@PathVariable("id") Long id, @PathVariable("username") String username, Model model, User user) {
		log.info("用户名:{}进行获取博客修改页面", username);
		JsonResponse<List<Category>> categoryResponse = categoryService.getCategoryList(Long.valueOf(user.getId()));
		JsonResponse<BlogWithBLOBs> blogResponse = blogService.getBlogById(id);
		model.addAttribute("blog", blogResponse.getData());
		model.addAttribute("categoryList", categoryResponse.getData());
		return new ModelAndView("/userspace/blog_edit", "blogModel", model);
	}
}
