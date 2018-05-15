package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Category;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author kangyonggan
 * @date 2018/04/02
 */
@Mapper
public interface CategoryMapper extends MyMapper<Category> {

    /**
     * 查找所有分类，带有小说总数
     *
     * @return
     */
    List<Category> selectAllCategoryWithNovelCount();
}