package com.kangyonggan.server.service.impl;

import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.mapper.MenuMapper;
import com.kangyonggan.server.model.Menu;
import com.kangyonggan.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @Log
    public List<Menu> findMenusByUsername(String username) {
        return menuMapper.selectMenusByUsername(username);
    }

}
