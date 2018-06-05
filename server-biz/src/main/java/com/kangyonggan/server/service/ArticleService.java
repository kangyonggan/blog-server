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
}
