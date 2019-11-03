package com.ccarlos.blog.dto;

import com.ccarlos.blog.model.Comment;
import com.ccarlos.blog.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description: 评论DTO
 * @author: ccarlos
 * @date: 2019/7/18 16:37
 */
@Getter
@Setter
@Builder
public class CommentDTO {

	//评论
	private Comment comment;

	//用户
	private User user;
}
