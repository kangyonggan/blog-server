package com.kangyonggan.server.mapper;

import com.kangyonggan.server.dto.Query;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
public interface ArticleMapper extends MyMapper<Article> {

    /**
     * 搜索文章
     *
     * @param query
     * @return
     */
    List<Article> searchArticles(@Param("query") Query query);
}