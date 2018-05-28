package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
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
     * @param params
     * @return
     */
    List<Section> findAllSections(Params params);

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

    /**
     * 查找后面100章
     *
     * @param code
     * @return
     */
    List<Section> findNext100Sections(Integer code);

    /**
     * 查找第一章
     *
     * @param novelCode
     * @return
     */
    Section findFirstSection(int novelCode);
}
