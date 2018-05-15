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

}
