package com.ccarlos.blog.service.impl;

import com.ccarlos.blog.dto.MongoFileDTO;
import com.ccarlos.blog.repository.MongoFileRepository;
import com.ccarlos.blog.service.MongoFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @description: Mongo文件服务实现
 * @author: ccarlos
 * @date: 2019/5/24 11:25
 */
@Service
public class MongoFileServiceImpl implements MongoFileService {

	@Autowired
	private MongoFileRepository mongoFileRepository;

	/**
	 * @description: 保存Mongo文件
	 * @author: ccarlos
	 * @date: 2019/5/27 10:00
	 * @param: mongoFileDTO mongo文件DTO
	 * @return: com.ccarlos.blog.dto.MongoFileDTO
	 */
	@Override
	public MongoFileDTO addFile(MongoFileDTO mongoFileDTO) {
		return mongoFileRepository.save(mongoFileDTO);
	}

	/**
	 * @description: 根据Id获取mongo文件
	 * @author: ccarlos
	 * @date: 2019/5/27 12:45
	  * @param: id 文件id
	 * @return: java.util.Optional<com.ccarlos.blog.dto.MongoFileDTO>
	 * @throws:
	 */
	@Override
	public Optional<MongoFileDTO> getMongoFileDTOById(String id) {
		return mongoFileRepository.findById(id);
//		return null;
	}
}
