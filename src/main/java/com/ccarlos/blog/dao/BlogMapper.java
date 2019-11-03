package com.ccarlos.blog.dao;


import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.MessageLog;

public interface BlogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogWithBLOBs record);

    int insertSelective(BlogWithBLOBs record);

    BlogWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BlogWithBLOBs record);

    int updateByPrimaryKey(Blog record);

    /**
     * @description: 更新博客相关的次数(阅读量、评论量、点赞量等)
     * @author: ccarlos
     * @date: 2019/6/28 1:16
     * @param: messageId 消息id
     * @return: int
     */
    int updateBlogCount(BlogWithBLOBs blogWithBLOBs);
}