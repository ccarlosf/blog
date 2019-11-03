package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.Comment;

import java.util.List;

public interface CommentMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    /**
     * @description: 根据博客id,联表查询评论表和博客评论关系表
     * @author: ccarlos
     * @date: 2019/7/18 11:41
      * @param: blogId
     * @return: java.util.List<com.ccarlos.blog.model.Comment>
     */
    List<Comment> selectCommentListByBlogId(Long blogId);
}