package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.constants.ApplyStatus;
import com.kangyonggan.server.constants.Status;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.ArticleMapper;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
@Service
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> searchArticles(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return articleMapper.searchArticles(params.getQuery());
    }

    @Override
    @Log
    public void saveArticle(Article article) {
        article.setApplyStatus(ApplyStatus.APPLY.getCode());
        myMapper.insertSelective(article);
    }

    @Override
    @Log
    public Article findArticleById(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(Status.ENABLE.getCode());
        return myMapper.selectOne(article);
    }

    @Override
    @Log
    public void updateArticle(Article article) {
        article.setApplyStatus(ApplyStatus.APPLY.getCode());
        myMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    @Log
    public void deleteArticle(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(Status.DISABLE.getCode());

        myMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    @Log
    public Article getArticle(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    @Log
    public void replyArticles(String ids, String type, String replyMsg) {
        Article article = new Article();
        article.setReplyMsg(replyMsg);
        article.setApplyStatus(type);

        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("applyStatus", ApplyStatus.APPLY.getCode())
                .andIn("id", Arrays.asList(ids.split(",")));

        myMapper.updateByExampleSelective(article, example);

    }
}
