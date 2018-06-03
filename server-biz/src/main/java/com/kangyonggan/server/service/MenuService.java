package com.kangyonggan.server.service;

import com.kangyonggan.server.model.Menu;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public interface MenuService {

    /**
     * 查找用户菜单
     *
     * @param username
     * @return
     */
    List<Menu> findMenusByUsername(String username);

    /**
     * 查找用户菜单代码
     *
     * @param username
     * @return
     */
    List<String> findMenuCodesByUsername(String username);
}
