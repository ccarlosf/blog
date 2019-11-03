package com.ccarlos.blog.common.constant;

/**
 * @description: 博客常量类
 * @author: ccarlos
 * @date: 2019/7/2 15:44
 */
public class BlogConst {

	/**
	 * @description: 博客阅读、评论、点赞数常量
	 * @author: ccarlos
	 * @date: 2019/7/2 15:46
	 */
	public interface Count{
		int READ_ADD_COUNT = 1; //博客阅读量递增次数
		int DEFAULT_READ_COUNT = 0; //初始化博客阅读量次数

		int COMMENT_ADD_COUNT=1; //博客评论量递增次数
		int DEFAULT_COMMENT_COUNT = 0; //初始化博客评论量次数

		int VOTE_ADD_COUNT=1; //博客点赞量递增次数
		int DEFAULT_VOTE_COUNT = 0; //初始化博客点赞量次数

	}
}
