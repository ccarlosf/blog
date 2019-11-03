package com.ccarlos.blog.dao;

import com.ccarlos.blog.model.BlogComment;
import com.ccarlos.blog.model.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCommentMapper {

    int insert(BlogComment record);

    int insertSelective(BlogComment record);

    /**
     * @description: 根据博客id获取博客评论关系列表
     * @author: ccarlos
     * @date: 2019/7/18 11:26
      * @param: blogId
     * @return: java.util.List<com.ccarlos.blog.model.BlogComment>
     */
    List<BlogComment> selectBlogCommentListByBlogId(Long blogId);

}