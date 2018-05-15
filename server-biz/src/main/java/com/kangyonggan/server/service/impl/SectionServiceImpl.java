package com.kangyonggan.server.service.impl;

import com.kangyonggan.server.model.Section;
import com.kangyonggan.server.service.SectionService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Service
public class SectionServiceImpl extends BaseService<Section> implements SectionService {
    @Override
    public List<Section> findAllSections(Integer novelCode) {
        Example example = new Example(Section.class);
        example.createCriteria().andEqualTo("novelCode", novelCode);
        example.setOrderByClause("code asc");

        return myMapper.selectByExample(example);
    }
}
