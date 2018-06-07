package com.kangyonggan.server.service;

import com.kangyonggan.server.model.Section;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
public interface SectionService {
    /**
     * 查找小说全部章节
     *
     * @param novelCode
     * @return
     */
    List<Section> findNovelSections(Integer novelCode);

    /**
     * 查找章节详情
     *
     * @param sectionCode
     * @return
     */
    Section findSectionByCode(Integer sectionCode);

    /**
     * 拉取最新章节
     *
     * @param novelCode
     */
    void updateSections(Integer novelCode);

    /**
     * 查找上一章
     *
     * @param code
     * @return
     */
    Section findPrevSectionByCode(Integer code);

    /**
     * 查找下一章
     *
     * @param code
     * @return
     */
    Section findNextSectionByCode(Integer code);
}
