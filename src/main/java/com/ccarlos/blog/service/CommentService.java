package com.ccarlos.blog.service;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dto.CommentDTO;
import com.ccarlos.blog.model.Category;
import com.ccarlos.blog.model.Comment;

import java.util.List;

/**
 * @description: 评论服务接口
 * @author: ccarlos
 * @date: 2019/7/18 10:05
 */
public interface CommentService {

	/**
	 * @description: 发表评论
	 * @author: ccarlos
	 * @date: 2019/7/18 10:06
	  * @param: blogId 博客id
      * @param: userId 用户id
	 * @param: commentContent 评论内容
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	JsonResponse<String> addComment(Long blogId,Integer userId, String commentContent);

	/**
	 * @description: 根据博客id获取评论列表
	 * @author: ccarlos
	 * @date: 2019/7/18 11:19
	  * @param: blogId
	 * @return: com.ccarlos.blog.common.JsonResponse<java.util.List<com.ccarlos.blog.model.Comment>>
	 */
	JsonResponse<List<CommentDTO>> getCommentList(Long blogId);
}
