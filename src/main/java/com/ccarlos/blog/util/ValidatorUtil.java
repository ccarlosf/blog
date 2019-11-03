package com.ccarlos.blog.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 校验工具类
 * @author: Created by ccarlos
 * @date: 2019/4/1 19:17
 */
public class ValidatorUtil {

    //邮箱验证正则表达式
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    public static boolean isEmail(String src) {
        if (StringUtils.isBlank(src)) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(src);
        return matcher.matches();
    }


}
