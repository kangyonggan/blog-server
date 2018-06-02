package com.kangyonggan.server.mapper;

import com.kangyonggan.server.model.Menu;
import com.kangyonggan.server.plugin.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/4/18
 */
public interface MenuMapper extends MyMapper<Menu> {

    /**
     * 查找用户菜单
     *
     * @param username
     * @return
     */
    List<Menu> selectMenusByUsername(@Param("username") String username);

}