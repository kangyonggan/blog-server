package com.kangyonggan.server.mapper;

import com.kangyonggan.server.dto.Query;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
public interface NovelMapper extends MyMapper<Novel> {

    /**
     * 搜索小说
     *
     * @param query
     * @return
     */
    List<Novel> searchNovels(@Param("query") Query query);
}