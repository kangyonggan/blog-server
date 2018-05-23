package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Novel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
public interface NovelService {

    /**
     * 笔趣阁地址
     */
    String BI_QU_GE_URL = "http://www.biquge.cn/";

    /**
     * 搜索小说
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    List<Novel> searchNovels(Params params) throws UnsupportedEncodingException;

    /**
     * 查找小说
     *
     * @param code
     * @return
     */
    Novel findNovelByCode(Integer code);
}
