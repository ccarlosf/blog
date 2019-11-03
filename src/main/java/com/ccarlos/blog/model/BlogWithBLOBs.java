package com.ccarlos.blog.model;

import com.github.rjeschke.txtmark.Processor;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * @description: 博文大字段内容实体类
 * @author: ccarlos
 * @date: 2019/5/28 10:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogWithBLOBs extends Blog {

	//markdown格式博文内容
/*	@Basic(fetch= FetchType.LAZY) // 懒加载
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)*/
//	@NotNull(message = "内容不能为空")
	private String content;

	//html格式博文内容
	/*@Basic(fetch=FetchType.LAZY) // 懒加载
	@NotEmpty(message = "内容不能为空")
	@Size(min=2)*/
//    @NotNull(message = "内容不能为空")
	private String htmlContent;

	public BlogWithBLOBs(Long id, String title, String blogDigest, String label, Integer readCount, Integer commentCount, Integer voteCount, Long categoryId, Long userId, String createMessageId, String updateMessageId, String createMessageType, String updateMessageType, Date createTime, Date updateTime, String content, String htmlContent) {
		super(id, title, blogDigest, label, readCount, commentCount, voteCount, categoryId, userId, createMessageId, updateMessageId, createMessageType, updateMessageType, createTime, updateTime);
		this.content = content;
		this.htmlContent = htmlContent;
	}

	public void setHtmlContent(String content) {
		this.htmlContent = Processor.process(content);
	}
}
