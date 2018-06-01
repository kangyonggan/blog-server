package com.kangyonggan.server.service.impl;

import com.kangyonggan.server.mapper.RoleMapper;
import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findUserRoles(String username) {
        return roleMapper.selectRolesByUsername(username);
    }
}
