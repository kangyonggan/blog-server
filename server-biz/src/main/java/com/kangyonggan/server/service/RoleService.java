package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Role;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface RoleService {

    /**
     * 判断角色代码是否存在
     *
     * @param code
     * @return
     */
    boolean existsRoleCode(String code);

    /**
     * 更新角色
     *
     * @param role
     */
    void updateRole(Role role);

    /**
     * 保存角色
     *
     * @param role
     */
    void saveRole(Role role);

    /**
     * 恢复角色
     *
     * @param codes
     */
    void recovery(String codes);

    /**
     * 删除角色
     *
     * @param codes
     */
    void delete(String codes);

    /**
     * 搜索角色
     *
     * @param params
     * @return
     */
    List<Role> searchRoles(Params params);
}
