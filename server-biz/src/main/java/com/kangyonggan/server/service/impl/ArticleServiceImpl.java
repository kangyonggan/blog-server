package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.ArticleMapper;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
