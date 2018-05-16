package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kangyonggan
 * @date 2018/04/02
 */
@Mapper
public interface NovelMapper extends MyMapper<Novel> {

}