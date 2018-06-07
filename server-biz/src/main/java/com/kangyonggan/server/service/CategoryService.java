package com.kangyonggan.server.service;

import com.kangyonggan.server.model.Category;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
public interface CategoryService {
    /**
     * 根据栏目类型查找栏目
     *
     * @param type
     * @return
     */
    List<Category> findCategoriesByType(String type);
}
