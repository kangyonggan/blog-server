package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.server.constants.Status;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.RoleMapper;
import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
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

    @Override
    public List<Role> findAllRoles() {
        Role role = new Role();
        role.setStatus(Status.ENABLE.getCode());

        return myMapper.select(role);
    }

    @Override
    public List<Role> searchRoles(Params params) {
        Example example = new Example(Role.class);

        Example.Criteria criteria = example.createCriteria();
        String code = params.getQuery().getString("code");
        if (StringUtils.isNotEmpty(code)) {
            criteria.andLike("code", StringUtil.toLike(code));
        }
        String name = params.getQuery().getString("name");
        if (StringUtils.isNotEmpty(name)) {
            criteria.andLike("name", StringUtil.toLike(name));
        }
        Date startDate = params.getQuery().getDate("startDate");
        if (startDate != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startDate);
        }
        Date endDate = params.getQuery().getDate("endDate");
        if (endDate != null) {
            criteria.andLessThanOrEqualTo("createdTime", endDate);
        }

        String sort = params.getAppSort();
        String order = params.getAppOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    public void updateRole(Role role) {
        myMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(Long id) {
        myMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void saveRole(Role role) {
        myMapper.insertSelective(role);
    }

    @Override
    public boolean existsRoleCode(String code) {
        Role role = new Role();
        role.setCode(code);
        return exists(role);
    }

    @Override
    public void updateRoleMenus(String code, String menuCodes) {
        roleMapper.deleteRoleMenus(code);

        if (StringUtils.isNotEmpty(menuCodes)) {
            roleMapper.insertRoleMenus(code, Arrays.asList(menuCodes.split(",")));
        }
    }
}
