package com.ccarlos.blog.controller.frontend;

import com.ccarlos.blog.dto.MongoFileDTO;
import com.ccarlos.blog.service.MongoFileService;
import com.ccarlos.blog.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * @description: mongo文件控制器
 * @author: ccarlos
 * @date: 2019/5/24 11:23
 */
@Slf4j
@RestController
@RequestMapping("/mongo/file")
public class MongoFileController {

	@Value("${mongo.serverIp}")
	private String serverIp;

	@Value("${mongo.serverPort}")
	private String serverPort;

	@Autowired
	private MongoFileService mongoFileService;

	/**
	 * @description: Mongo文件上传
	 * @author: ccarlos
	 * @date: 2019/5/27 10:08
	 * @param: file 文件
	 * @return: org.springframework.http.ResponseEntity<java.lang.String>
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		try {
			MongoFileDTO mongoFileDTO = new MongoFileDTO(file.getOriginalFilename(), file.getContentType(), file.getSize(),
					new Binary(file.getBytes()));
			mongoFileDTO.setMd5(MD5Util.getMD5(file.getInputStream()));
			MongoFileDTO returnMongoFileDTO = mongoFileService.addFile(mongoFileDTO);
			String path = "//" + serverIp + ":" + serverPort + "/mongo/file/online/" + returnMongoFileDTO.getId();
			return ResponseEntity.status(HttpStatus.OK).body(path);
		} catch (IOException e) {
			log.error("Mongo文件上传发生异常，异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log.error("Mongo文件上传发生异常，异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * @description: 在线显示mongo文件
	 * @author: ccarlos
	 * @date: 2019/5/27 12:35
	 * @param: id 文件id
	 * @return: org.springframework.http.ResponseEntity<java.lang.Object>
	 */
	@GetMapping("/online/{id}")
	@ResponseBody
	public ResponseEntity<Object> mongoFileOnline(@PathVariable String id) {
		Optional<MongoFileDTO> mongoFileDTO = mongoFileService.getMongoFileDTOById(id);
		if (mongoFileDTO.isPresent()) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + mongoFileDTO.get().getName() + "\"")
					.header(HttpHeaders.CONTENT_TYPE, mongoFileDTO.get().getContentType())
					.header(HttpHeaders.CONTENT_LENGTH, mongoFileDTO.get().getSize() + "").header("Connection", "close")
					.body(mongoFileDTO.get().getContent().getData());
		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文件未找到");
		}
	}
}
