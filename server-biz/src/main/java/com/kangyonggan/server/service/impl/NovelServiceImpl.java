package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.NovelMapper;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@Service
public class NovelServiceImpl extends BaseService<Novel> implements NovelService {

    @Autowired
    private NovelMapper novelMapper;

    @Override
    public List<Novel> searchNovels(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return novelMapper.searchNovels(params.getQuery());
    }

    @Override
    public void updateNovel(Novel novel) {
        myMapper.updateByPrimaryKeySelective(novel);
    }

    @Override
    public Novel findNovelByCode(Integer code) {
        Novel novel = new Novel();
        novel.setCode(code);
        return myMapper.selectOne(novel);
    }
}
