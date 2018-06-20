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

    /**
     * 删除文章
     *
     * @param id
     */
    void deleteArticle(Long id);

    /**
     * 获取文章
     *
     * @param id
     * @return
     */
    Article getArticle(Long id);

    /**
     * 审批
     *
     * @param ids
     * @param type
     * @param replyMsg
     */
    void replyArticles(String ids, String type, String replyMsg);

    /**
     * 生成RSS
     * 
     * @param username
     */
    void genRss(String username);

    /**
     * 查找上一篇文章
     *
     * @param id
     * @return
     */
    Article findPrevArticle(Long id);

    /**
     * 查找下一篇文章
     *
     * @param id
     * @return
     */
    Article findNextArticle(Long id);
}
