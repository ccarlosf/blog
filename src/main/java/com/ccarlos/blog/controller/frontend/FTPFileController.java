package com.ccarlos.blog.controller.frontend;

import com.ccarlos.blog.common.JsonResponse;
import com.ccarlos.blog.service.FTPFileService;
import com.ccarlos.blog.util.PropertiesUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 文件控制器
 * @author: ccarlos
 * @date: 2019/5/17 19:38
 */
@RestController
@RequestMapping("/ftp/file")
public class FTPFileController {

	@Autowired
	private FTPFileService fileService;

	/**
	 * @description: 文件上传
	 * @author: ccarlos
	 * @date: 2019/5/18 9:38
	 * @param: file 文件
	 * @param: request 请求
	 * @return: com.ccarlos.blog.common.JsonResponse
	 */
	@PostMapping("/upload")
	public JsonResponse upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.upload(file, path);
		String url = PropertiesUtil.getProperty("ftp.httpPrefix") + targetFileName;
		Map fileMap = Maps.newHashMap();
		fileMap.put("uri", targetFileName);
		fileMap.put("url", url);
		return JsonResponse.createBySuccess(fileMap);
	}


	/**
	 * @description: 富文本文件上传
	 * @author: ccarlos
	 * @date: 2019/5/18 10:07
	 * @param: file 文件
	 * @param: request 请求
	 * @param: response 响应
	 * @return: java.util.Map
	 */
	@PostMapping("/rich/upload")
	public Map richImgUpload(@RequestParam(value = "rich_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		Map resultMap = Maps.newHashMap();
		//富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = fileService.upload(file, path);
		if (StringUtils.isBlank(targetFileName)) {
			resultMap.put("success", false);
			resultMap.put("msg", "上传失败");
			return resultMap;
		}
		String url = PropertiesUtil.getProperty("ftp.httpPrefix") + targetFileName;
		resultMap.put("success", true);
		resultMap.put("msg", "上传成功");
		resultMap.put("file_path", url);
		response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
		return resultMap;
	}
}
