package com.kangyonggan.server.service.impl;

import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.mapper.CategoryMapper;
import com.kangyonggan.server.model.Category;
import com.kangyonggan.server.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Service
public class CategoryServiceImpl extends BaseService<Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    @Log
    public List<Category> findCategoriesByType(String type) {
        if (StringUtils.isEmpty(type)) {
            return new ArrayList<>();
        }
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("type", type);

        example.setOrderByClause("sort asc");

        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    public List<Category> findAllCategoryWithNovelCount() {
        return categoryMapper.selectAllCategoryWithNovelCount();
    }
}
