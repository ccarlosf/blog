package com.ccarlos.blog.validator;

import com.ccarlos.blog.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description: 邮箱格式验证类
 * @author: Created by ccarlos
 * @date: 2019/4/1 19:10
 */
public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {

    //默认是否验证(否)
    private boolean required = false;

    @Override
    public void initialize(IsEmail isEmail) {
        required = isEmail.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isEmail(s);
        } else {
            //TODO 逻辑
            if (StringUtils.isEmpty(s)) {
                return true;
            } else {
                return ValidatorUtil.isEmail(s);
            }
        }
    }
}
