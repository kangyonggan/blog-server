package com.kangyonggan.server.service;

import com.kangyonggan.server.model.Section;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
public interface SectionService {

    /**
     * 查找所有章节
     *
     * @param novelCode
     * @return
     */
    List<Section> findAllSections(Integer novelCode);

    /**
     * 拉取最新章节
     *
     * @param novelCode
     */
    void updateSections(Integer novelCode);

    /**
     * 查找章节
     *
     * @param code
     * @return
     */
    Section findSectionByCode(Integer code);

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
