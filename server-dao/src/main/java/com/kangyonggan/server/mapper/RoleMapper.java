package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
@Mapper
public interface RoleMapper extends MyMapper<Role> {

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    List<Role> selectRolesByUsername(@Param("username") String username);

    /**
     * 删除用户角色
     *
     * @param username
     */
    void deleteAllRolesByUsername(@Param("username") String username);
}