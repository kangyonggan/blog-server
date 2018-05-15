package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.model.Novel;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
public interface NovelService {

    /**
     * 搜索小说
     *
     * @param params
     * @return
     */
    List<Novel> searchNovels(Params params);

}
