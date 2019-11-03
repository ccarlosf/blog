package com.ccarlos.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件服务接口
 * @author: ccarlos
 * @date: 2019/5/17 19:38
 */
public interface FTPFileService {

	/**
	 * @description: 上传图片
	 * @author: ccarlos
	 * @date: 2019/5/17 19:40
	 * @param: file 文件
	 * @param: path 路径
	 * @return: java.lang.String
	 */
	String upload(MultipartFile file, String path);
}
