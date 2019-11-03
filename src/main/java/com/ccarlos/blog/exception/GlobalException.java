package com.ccarlos.blog.exception;

import com.ccarlos.blog.common.CodeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 全局异常类
 * @author: Created by ccarlos
 * @date: 2019/4/3 11:00
 */
@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException{

   private static final long serialVersionUID = -3089286571634048948L;

   private CodeMessage codeMessage;

}
