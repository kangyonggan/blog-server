package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
@Mapper
public interface RoleMapper extends MyMapper<Role> {
}