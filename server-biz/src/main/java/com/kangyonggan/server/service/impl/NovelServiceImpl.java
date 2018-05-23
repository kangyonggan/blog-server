package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.service.NovelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Service
public class NovelServiceImpl extends BaseService<Novel> implements NovelService {

    @Override
    public List<Novel> searchNovels(Params params) throws UnsupportedEncodingException {
        Example example = new Example(Novel.class);

        Example.Criteria criteria = example.createCriteria();
        String categoryCode = params.getQuery().getString("categoryCode");
        if (StringUtils.isNotEmpty(categoryCode)) {
            criteria.andEqualTo("categoryCode", categoryCode);
        }

        String key = params.getQuery().getString("key");
        key = new String(key.getBytes("ISO-8859-1"), "UTF-8");
        if (StringUtils.isNotEmpty(key)) {
            criteria.andLike("name", StringUtil.toLike(key));
            example.or(example.createCriteria().andLike("author", StringUtil.toLike(key)));
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    public Novel findNovelByCode(Integer code) {
        Novel novel = new Novel();
        novel.setCode(code);

        return myMapper.selectOne(novel);
    }
}
