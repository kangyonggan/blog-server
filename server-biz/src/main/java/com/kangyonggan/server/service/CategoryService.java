package com.kangyonggan.server.service;

import com.kangyonggan.server.model.Category;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
public interface CategoryService {

    /**
     * 查找指定类型的栏目
     *
     * @param type
     * @return
     */
    List<Category> findCategoriesByType(String type);

    /**
     * 查找所有分类，带有小说总数
     *
     * @return
     */
    List<Category> findAllCategoryWithNovelCount();

}
