package com.ccarlos.blog.config.dao.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @description: Druid过滤器
 * @author: Created by ccarlos
 * @date: 2019/6/17 17:19
 */
@WebFilter(filterName = "DruidFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
})
public class DruidFilter extends WebStatFilter {
}
