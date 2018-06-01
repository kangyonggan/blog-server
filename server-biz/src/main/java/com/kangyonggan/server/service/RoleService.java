package com.kangyonggan.server.service;

import com.kangyonggan.server.model.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface RoleService {

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    List<Role> findUserRoles(String username);

    /**
     * 查找所有角色
     *
     * @return
     */
    List<Role> findAllRoles();

}
