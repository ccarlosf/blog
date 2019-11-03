package com.ccarlos.blog.controller.frontend;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dto.CommentDTO;
import com.ccarlos.blog.model.*;
import com.ccarlos.blog.service.BlogService;
import com.ccarlos.blog.service.CommentService;
import com.ccarlos.blog.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 评论控制器
 * @author: ccarlos
 * @date: 2019/7/18 9:56
 */
@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private BlogService blogService;

	/**
	 * @description: 发布评论
	 * @author: ccarlos
	 * @date: 2019/7/18 10:45
	 * @param: blogId 博客id
	 * @param: commentContent 评论内容
	 * @param: user 评论用户
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@PostMapping
	@ResponseBody
	public JsonResponse<String> addComment(Long blogId, String commentContent, User user) {
		log.info("博客id:{},发表评论内容:{}", blogId, commentContent);
		return commentService.addComment(blogId, user.getId(), commentContent);
	}

	/**
	 * @description: 获取评论列表
	 * @author: ccarlos
	 * @date: 2019/7/18 10:46
	 * @param: blogId
	 * @param: model
	 * @return: java.lang.String
	 */
	@GetMapping("/list")
	public String getCommentList(@RequestParam(value = "blogId", required = true) Long blogId,User user, Model model) {
		JsonResponse<List<CommentDTO>> commentDTOResponse = commentService.getCommentList(blogId);
		model.addAttribute("comments", commentDTOResponse.getData());
//		model.addAttribute("username",user);
		return "/userspace/blog :: #mainContainerRepleace";
	}

}
