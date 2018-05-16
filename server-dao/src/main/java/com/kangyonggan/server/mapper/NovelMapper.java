package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @date 2018/04/02
 */
@Mapper
public interface NovelMapper extends MyMapper<Novel> {


    /**
     * 搜索小说
     *
     * @param code
     * @param name
     * @param author
     * @param categoryCode
     * @return
     */
    List<Novel> searchNovels(@Param("code") String code, @Param("name") String name, @Param("author") String author, @Param("categoryCode") String categoryCode);

}