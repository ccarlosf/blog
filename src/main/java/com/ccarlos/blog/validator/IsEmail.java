package com.ccarlos.blog.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description: 验证邮箱格式的注解类
 * @author: Created by ccarlos
 * @date: 2019/3/31 20:27
 */
//TODO
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsEmailValidator.class}) //绑定邮箱校验器
public @interface IsEmail {

    //是否必须，默认(是)
    boolean required() default false;

    //校验不通过时的，信息提示
    String message() default "邮箱格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

