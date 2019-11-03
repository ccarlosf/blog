package com.ccarlos.blog.repository;

import com.ccarlos.blog.dto.MongoFileDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @description: Mongo文件数据存储仓库接口
 * @author: ccarlos
 * @date: 2019/5/27 10:03
 */
public interface MongoFileRepository extends MongoRepository<MongoFileDTO, String> {
}
