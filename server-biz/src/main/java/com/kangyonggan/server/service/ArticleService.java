package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Article;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
public interface ArticleService {
    /**
     * 搜索我的文章
     *
     * @param params
     * @return
     */
    List<Article> searchArticles(Params params);

    /**
     * 保存文章
     *
     * @param article
     */
    void saveArticle(Article article);

    /**
     * 查找文章
     *
     * @param id
     * @return
     */
    Article findArticleById(Long id);

    /**
     * 更新文章
     *
     * @param article
     */
    void updateArticle(Article article);

}
