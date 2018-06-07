package com.kangyonggan.server.service.impl;

import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.constants.Status;
import com.kangyonggan.server.model.Category;
import com.kangyonggan.server.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@Service
public class CategoryServiceImpl extends BaseService<Category> implements CategoryService {

    @Override
    @Log
    public List<Category> findCategoriesByType(String type) {
        Example example = new Example(Category.class);

        example.createCriteria().andEqualTo("type", type)
                .andEqualTo("status", Status.ENABLE.getCode());

        example.setOrderByClause("sort asc");
        return myMapper.selectByExample(example);
    }
}
