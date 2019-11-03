package com.ccarlos.blog.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @description: Json格式返回结果封装类
 * @author: Created by ccarlos
 * @date: 2019/2/28 21:57
 */
@Getter
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候，如果是null对象，key也会消失
public class JsonResponse<T> implements Serializable {

	//序列化ID
	private static final long serialVersionUID = 9156002637959364171L;

	//返回结果状态码
	private int status;

	//返回结果信息提示
	private String message;

	//返回结果封装数据
	private T data;

	/**
	 * @description: 构造函数
	 * @author: ccarlos
	 * @date: 2019/2/28 22:03
	 * @param: status 状态码
	 */
	private JsonResponse(int status) {
		this.status = status;
	}

	/**
	 * @description: 构造函数
	 * @author: ccarlos
	 * @date: 2019/2/28 22:05
	 * @param: status 状态码
	 * @param: data 封装数据
	 */
	private JsonResponse(int status, T data) {
		this.status = status;
		this.data = data;
	}

	/**
	 * @description: 构造函数
	 * @author: ccarlos
	 * @date: 2019/2/28 22:10
	 * @param: status 状态码
	 * @param: message 结果信息提示
	 * @param: data 封装数据
	 */
/*    private JsonResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }*/


	/**
	 * @description: 构造函数
	 * @author: ccarlos
	 * @date: 2019/2/28 22:13
	 * @param: status 状态码
	 * @param: message 结果信息提示
	 */
	private JsonResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * @description: 判断响应是否成功
	 * @author: ccarlos
	 * @date: 2019/2/28 22:15
	 * @return: boolean
	 */
	//TODO boolean与Boolean
	//使之不在json序列化结果中
	@JsonIgnore
	public boolean isSuccess() {
		return this.status == ResponseCode.SUCCESS.getCode();
	}


	/**
	 * @description: 成功响应时调用的方法，带成功响应状态代码，不带描述
	 * @author: ccarlos
	 * @date: 2019/2/28 22:17
	 * @param: <T> 数据类型
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
	//TODO static类型?
	public static <T> JsonResponse<T> createBySuccess() {
		return new JsonResponse<T>(ResponseCode.SUCCESS.getCode());
	}

	/**
	 * @description: 成功响应时调用的方法，带成功响应状态代码和提示信息
	 * @author: ccarlos
	 * @date: 2019/2/28 22:18
	 * @param: message 提示信息
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
	public static <T> JsonResponse<T> createBySuccessMessage(String message) {
		return new JsonResponse<T>(ResponseCode.SUCCESS.getCode(), message);
	}

	/**
	 * @description: 成功响应时调用的方法，带成功响应状态代码和封装数据
	 * @author: ccarlos
	 * @date: 2019/2/28 22:20
	 * @param: data 封装数据
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
	public static <T> JsonResponse<T> createBySuccess(T data) {
		return new JsonResponse<T>(ResponseCode.SUCCESS.getCode(), data);
	}

	/**
	 * @description: 成功响应时调用的方法，带成功响应状态代码、提示信息和封装数据
	 * @author: ccarlos
	 * @date: 2019/2/28 22:22
	 * @param: message 提示信息
	 * @param: data 封装数据
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
	public static <T> JsonResponse<T> createBySuccess(String message, T data) {
		return new JsonResponse<T>(ResponseCode.SUCCESS.getCode(), message, data);
	}

	/**
	 * @description: 失败响应时调用的方法，带失败响应状态码，和失败响应自带的描述信息
	 * @author: ccarlos
	 * @date: 2019/2/28 22:23
	 * @param: <T> 数据类型
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
	public static <T> JsonResponse<T> createByError() {
		return new JsonResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
	}

	/**
	 * @description: 失败响应时调用的方法，带失败响应状态码，和业务错误提示信息
	 * @author: ccarlos
	 * @date: 2019/2/28 22:26
	 * @param: errorMessage 业务错误提示信息
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
	public static <T> JsonResponse<T> createByErrorMessage(String errorMessage) {
		return new JsonResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
	}

	/**
	 * @description: 失败响应时调用的方法，带失败响应业务失败状态码，和业务错误提示信息
	 * @author: ccarlos
	 * @date: 2019/2/28 22:29
	 * @param: errorCode 业务失败状态码
	 * @param: errorMessage 业务错误提示信息
	 * @return: com.ccarlos.blog.common.JsonResponse<T>
	 */
    public static <T> JsonResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new JsonResponse<T>(errorCode, errorMessage);
    }

    /**
     * @description: 失败响应时调用的方法，带失败响应业务失败状态码，和业务错误提示信息
     * @author: ccarlos
     * @date: 2019/6/2 11:20
      * @param: codeMessage 消息封装体
     * @return: com.ccarlos.blog.common.JsonResponse<T>
     */
	public static <T> JsonResponse<T> createByErrorCodeMessage(CodeMessage codeMessage) {
		return new JsonResponse<T>(codeMessage.getCode(), codeMessage.getMessage());
	}

}
