package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Service
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {

    @Override
    public List<Article> searchArticles(Params params) {
        Example example = new Example(Article.class);

        Example.Criteria criteria = example.createCriteria();
        String title = params.getQuery().getString("title");
        if (StringUtils.isNotEmpty(title)) {
            criteria.andLike("title", StringUtil.toLike(title));
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }
}
