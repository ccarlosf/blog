package com.ccarlos.blog.service.impl;

import com.ccarlos.blog.service.FTPFileService;
import com.ccarlos.blog.util.FTPServerUtil;
import com.ccarlos.blog.util.UUIDUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @description: 文件服务实现
 * @author: ccarlos
 * @date: 2019/5/17 19:39
 */
@Slf4j
@Service
public class FTPFileServiceImpl implements FTPFileService {

	/**
	 * @description: 上传图片
	 * @author: ccarlos
	 * @date: 2019/5/17 19:41
	 * @param: file 文件
	 * @param: path 路径
	 * @return: java.lang.String
	 */
	@Override
	public String upload(MultipartFile file, String path) {
		String fileName = file.getOriginalFilename();
		//扩展名
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUIDUtil.getUUID() + "." + fileExtensionName;
		log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}", fileName, path, uploadFileName);

		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File targetFile = new File(path, uploadFileName);

		try {
			//文件已经上传成功了
			file.transferTo(targetFile);
			//已经上传到ftp服务器上
			FTPServerUtil.uploadFile(Lists.newArrayList(targetFile));
		} catch (IOException e) {
			log.error("上传文件发生异常", e);
			return null;
		}
		return targetFile.getName();
	}

}
