package com.ccarlos.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.dao.BlogCommentMapper;
import com.ccarlos.blog.dao.CommentMapper;
import com.ccarlos.blog.dao.UserMapper;
import com.ccarlos.blog.dto.CommentDTO;
import com.ccarlos.blog.model.BlogComment;
import com.ccarlos.blog.model.Comment;
import com.ccarlos.blog.model.User;
import com.ccarlos.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 评论服务实现
 * @author: ccarlos
 * @date: 2019/7/18 10:07
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private BlogCommentMapper blogCommentMapper;

	@Autowired
	private UserMapper userMapper;

	/**
	 * @description: 发表评论
	 * @author: ccarlos
	 * @date: 2019/7/18 10:06
	 * @param: blogId 博客id
	 * @param: userId 用户id
	 * @param: commentContent 评论内容
	 * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
	 */
	@Override
	@Transactional
	public JsonResponse<String> addComment(Long blogId, Integer userId, String commentContent) {

		//添加到评论表
		Comment comment = Comment.builder().userId(Long.valueOf(userId))
				.content(commentContent).build();
		int insertCommentCount = commentMapper.insert(comment);
		log.info("博客id:{},用户id:{},博客内容:{}添加到评论表后,返回的评论id:{}", blogId, userId, commentContent, comment.getId());

		if (insertCommentCount > 0) {
			//添加到博客评论关系表
			BlogComment blogComment = BlogComment.builder().blogId(blogId)
					.commentId(comment.getId()).build();
			int insertBlogCommentCount = blogCommentMapper.insert(blogComment);
			if (insertBlogCommentCount == 0) {
				return JsonResponse.createByErrorMessage("添加到博客评论关系表失败");
			}
		} else {
			return JsonResponse.createByErrorMessage("添加到评论表失败");
		}
		return JsonResponse.createBySuccessMessage("添加评论成功");
	}

	/**
	 * @description: 根据博客id获取评论列表
	 * @author: ccarlos
	 * @date: 2019/7/18 11:19
	 * @param: blogId
	 * @return: com.ccarlos.blog.common.JsonResponse<java.util.List               <               com.ccarlos.blog.model.Comment>>
	 */
	@Override
	public JsonResponse<List<CommentDTO>> getCommentList(Long blogId) {
		List<BlogComment> blogCommentList = blogCommentMapper.selectBlogCommentListByBlogId(blogId);
		log.info("博客id:{},博客关系表数据:{}", blogId, JSON.toJSONString(blogCommentList));
		List<Comment> commentList = blogCommentList.stream().map(blogComment -> {
			Comment comment = commentMapper.selectByPrimaryKey(blogComment.getCommentId());
			return comment;
		}).collect(Collectors.toList());
		log.info("博客id:{},排序前评论数据:{}", blogId, JSON.toJSONString(commentList));
		//TODO 简写 降序 是否需要判空
		/*Collections.sort(commentList,(o1,o2)->{
			if(o1.getCreateTime().compareTo(o2.getCreateTime())>0){
				return 1;
			}
			if(o1.getCreateTime().compareTo(o2.getCreateTime())<0){
				return -1;
			}
			return 0;
		});*/
		Collections.sort(commentList, new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				return o1.getCreateTime().compareTo(o2.getCreateTime());
			}
		});
		log.info("博客id:{},排序评论数据:{}", blogId, JSON.toJSONString(commentList));

		List<CommentDTO> commentDTOList = commentList.stream().map(comment -> {
			User user=userMapper.selectUserById(comment.getUserId().intValue());
			CommentDTO commentDTO=CommentDTO.builder().user(user).comment(comment).build();
			return commentDTO;
		}).collect(Collectors.toList());

		if (CollectionUtils.isNotEmpty(commentDTOList)) {
			return JsonResponse.createBySuccess(commentDTOList);
		}
		return JsonResponse.createBySuccess("没有查询到评论列表", null);
	}
}
