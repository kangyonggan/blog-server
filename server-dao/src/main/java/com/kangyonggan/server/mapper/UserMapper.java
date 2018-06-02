package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.User;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
public interface UserMapper extends MyMapper<User> {
    /**
     * 插入用户角色
     *
     * @param username
     * @param roleCodes
     */
    void insertUserRoles(@Param("username") String username, @Param("roleCodes") List<String> roleCodes);
}