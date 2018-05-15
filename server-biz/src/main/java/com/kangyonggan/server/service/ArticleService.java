package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Article;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
public interface ArticleService {

    /**
     * 搜索文章
     *
     * @param params
     * @return
     */
    List<Article> searchArticles(Params params);

    /**
     * 查找文章
     *
     * @param id
     * @return
     */
    Article findArticleById(Long id);

}
