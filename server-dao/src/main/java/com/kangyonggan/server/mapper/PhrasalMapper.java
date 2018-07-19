package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Phrasal;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
public interface PhrasalMapper extends MyMapper<Phrasal> {
    /**
     * 批量保存成语
     *
     * @param phrasals 成语列表
     */
    void savePhrasals(@Param("phrasals") List<Phrasal> phrasals);
}