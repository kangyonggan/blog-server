package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.server.constants.YesNo;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.RoleMapper;
import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public boolean existsRoleCode(String code) {
        Role role = new Role();
        role.setCode(code);
        return super.exists(role);
    }

    @Override
    public void updateRole(Role role) {
        myMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void saveRole(Role role) {
        myMapper.insertSelective(role);
    }

    @Override
    public void recovery(String codes) {
        if (StringUtils.isEmpty(codes)) {
            return;
        }
        Role role = new Role();
        role.setIsDeleted(YesNo.NO.getCode());
        Example example = new Example(Role.class);
        example.createCriteria().andIn("code", Arrays.asList(codes.split(",")));

        myMapper.updateByExampleSelective(role, example);
    }

    @Override
    public void delete(String codes) {
        if (StringUtils.isEmpty(codes)) {
            return;
        }
        Role role = new Role();
        role.setIsDeleted(YesNo.YES.getCode());
        Example example = new Example(Role.class);
        example.createCriteria().andIn("code", Arrays.asList(codes.split(",")));

        myMapper.updateByExampleSelective(role, example);
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
        Date startCreatedTime = params.getQuery().getDate("startCreatedTime");
        if (startCreatedTime != null) {
            criteria.andGreaterThanOrEqualTo("createdTime", startCreatedTime);
        }
        Date endCreatedTime = params.getQuery().getDate("endCreatedTime");
        if (endCreatedTime != null) {
            criteria.andLessThanOrEqualTo("createdTime", endCreatedTime);
        }

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("id desc");
        }

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return myMapper.selectByExample(example);
    }

    @Override
    public List<Role> findUserRoles(String username) {
        return roleMapper.selectRolesByUsername(username);
    }

    @Override
    public List<Role> findAllRoles() {
        Role role = new Role();
        role.setIsDeleted(YesNo.NO.getCode());
        return myMapper.select(role);
    }
}
