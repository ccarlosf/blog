package com.ccarlos.blog.dto;

import com.ccarlos.blog.model.Blog;
import com.ccarlos.blog.model.BlogWithBLOBs;
import com.ccarlos.blog.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @description: ES博客搜索DTO文档类
 * @author: ccarlos
 * @date: 2019/5/27 17:09
 */
/*@Getter
@Setter
@Document(indexName = "blog", type = "blog")
@XmlRootElement // MediaType 转为 XML
@NoArgsConstructor
@AllArgsConstructor
public class ESBlogDTO implements Serializable {
	private static final long serialVersionUID = -1174292028681544910L;

	@Id  // 主键
	private String id;

	//Blog的id  不做全文检索字段
	private Long blogId;

	//标题
	private String title;

	//摘要
	private String blogDigest;

	//markdown格式博文内容
	private String content;

	// 标签
	private String label;

	//阅读量 不做全文检索字段
	private Integer readCount = 0;

	//评论量 不做全文检索字段
	private Integer commentCount = 0;

	//点赞量 不做全文检索字段
	private Integer voteCount = 0;

	//用户名 不做全文检索字段
	private String username;

	//用户头像地址 不做全文检索字段
	private String avatar;

	//创建时间 不做全文检索字段
	private Timestamp createTime;

	//更新时间 不做全文检索字段
	private Timestamp updateTime;

	public ESBlogDTO(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public ESBlogDTO(Long blogId, String title, String blogDigest, String content, String label, Integer readCount, Integer commentCount, Integer voteCount, String username, String avatar, Timestamp createTime, Timestamp updateTime) {
		this.blogId = blogId;
		this.title = title;
		this.blogDigest = blogDigest;
		this.content = content;
		this.label = label;
		this.readCount = readCount;
		this.commentCount = commentCount;
		this.voteCount = voteCount;
		this.username = username;
		this.avatar = avatar;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public ESBlogDTO(Blog blog, BlogWithBLOBs blogWithBLOBs, User user) {
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.blogDigest = blog.getBlogDigest();
		this.content = blogWithBLOBs.getContent();
		this.username = user.getUsername();
		this.avatar = user.getAvatar();
		this.createTime = (Timestamp) blog.getCreateTime();
		this.updateTime = (Timestamp) blog.getUpdateTime();
		this.readCount = blog.getReadCount();
		this.commentCount = blog.getCommentCount();
		this.voteCount = blog.getVoteCount();
		this.label = blog.getLabel();
	}*/

	/*public void update(Blog blog) {
		this.blogId = blog.getId();
		this.title = blog.getTitle();
		this.summary = blog.getSummary();
		this.content = blog.getContent();
		this.username = blog.getUser().getUsername();
		this.avatar = blog.getUser().getAvatar();
		this.createTime = blog.getCreateTime();
		this.readSize = blog.getReadSize();
		this.commentSize = blog.getCommentSize();
		this.voteSize = blog.getVoteSize();
		this.tags = blog.getTags();
	}*/

	/*@Override
	public String toString() {
		return String.format(
				"User[id=%d, title='%s', content='%s']",
				blogId, title, content);
	}*/
/*}*/
