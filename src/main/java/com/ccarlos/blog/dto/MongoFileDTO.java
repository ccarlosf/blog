package com.ccarlos.blog.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @description: mongo文件dto
 * @author: ccarlos
 * @date: 2019/5/24 11:45
 */
@Getter
@Setter
@Document
public class MongoFileDTO {

	@Id  // 主键
	private String id;

	// 文件名称
	private String name;

	// 文件类型
	private String contentType;

	//文件大小
	private long size;

	//更新时间
	private Date uploadDate;

	//加密
	private String md5;

	// 文件内容
	private Binary content;

	// 文件路径
	private String path;

	public MongoFileDTO(String name, String contentType, long size,Binary content){
		this.name = name;
		this.contentType = contentType;
		this.size = size;
		this.uploadDate = new Date();
		this.content = content;
	}
}
