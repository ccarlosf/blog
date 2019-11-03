package com.ccarlos.blog.service;

import com.ccarlos.blog.dto.MongoFileDTO;

import java.util.Optional;

/**
 * @description: Mongo文件服务接口
 * @author: ccarlos
 * @date: 2019/5/24 11:25
 */
public interface MongoFileService {

	/**
	 * @description: 保存Mongo文件
	 * @author: ccarlos
	 * @date: 2019/5/27 9:59
	 * @param: mongoFileDTO mongo文件DTO
	 * @return: com.ccarlos.blog.dto.MongoFileDTO
	 */
	MongoFileDTO addFile(MongoFileDTO mongoFileDTO);

	/**
	 * @description: 根据Id获取mongo文件
	 * @author: ccarlos
	 * @date: 2019/5/27 12:44
	 * @param: id 文件id
	 * @return: java.util.Optional<com.ccarlos.blog.dto.MongoFileDTO>
	 */
	Optional<MongoFileDTO> getMongoFileDTOById(String id);
}
