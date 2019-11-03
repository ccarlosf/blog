package com.ccarlos.blog.exception;

import com.ccarlos.blog.common.CodeMessage;
import com.ccarlos.blog.common.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 全局异常处理器
 * @author: Created by ccarlos
 * @date: 2019/4/3 11:04
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * @description: 全局异常处理
     * @author: ccarlos
     * @date: 2019/4/3 11:15
     * @param: e 异常
     * @param: request 请求
     * @return: com.ccarlos.blog.common.JsonResponse<java.lang.String>
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResponse<String> handleGlobalException(Exception e, HttpServletRequest request) {
        log.error("异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return JsonResponse.createByErrorCodeMessage(globalException.getCodeMessage());
        } else if (e instanceof BindException) { //BindException是Spring框架抛出的Validation异常
            //TODO 显示所有的异常原因
            BindException bindException = (BindException) e;
            List<ObjectError> objectErrorList = bindException.getAllErrors();
            ObjectError objectError = objectErrorList.get(0);
            String message = objectError.getDefaultMessage();
            return JsonResponse.createByErrorCodeMessage(CodeMessage.BIND_ERROR.fillParameters(message));
        } else {
            return JsonResponse.createByErrorCodeMessage(CodeMessage.SERVER_ERROR);
        }
    }
}
