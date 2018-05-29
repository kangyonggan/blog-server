package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.User;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface UserService {

    /**
     * 搜索用户
     *
     * @param params
     * @return
     */
    List<User> searchUsers(Params params);

    /**
     * 删除
     *
     * @param usernames
     */
    void delete(String usernames);

    /**
     * 恢复
     *
     * @param usernames
     */
    void recovery(String usernames);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 修改密码
     *
     * @param user
     * @param usernames
     */
    void updatePassword(User user, String usernames);

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    boolean existsUsername(String username);

    /**
     * 更新用户角色
     *
     * @param username
     * @param roleCodes
     */
    void updateUserRoles(String username, String roleCodes);
}
