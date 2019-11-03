package com.ccarlos.blog.dao;


import com.ccarlos.blog.model.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /**
     * @description: 根据用户Id、分类名查询分类数量
     * @author: ccarlos
     * @date: 2019/5/30 19:43
      * @param: categoryName 分类名
     * @param: userId 用户id
     * @return: int
     */
    int checkCategoryNameByUserId(@Param("categoryName") String categoryName, @Param("userId") Long userId);

    /**
     * @description: 根据用户Id、分类名查询分类列表
     * @author: ccarlos
     * @date: 2019/5/30 19:48
      * @param: categoryName 分类名
     * @param: userId 用户id
     * @return: java.util.List<com.ccarlos.blog.model.Category>
     */
    List<Category> selectCategoryListByUserId(@Param("categoryName") String categoryName, @Param("userId") Long userId);
}