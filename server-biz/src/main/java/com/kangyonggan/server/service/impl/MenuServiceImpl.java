package com.kangyonggan.server.service.impl;

import com.kangyonggan.app.util.Collections3;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.mapper.MenuMapper;
import com.kangyonggan.server.model.Menu;
import com.kangyonggan.server.service.MenuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
        List<Menu> menus = menuMapper.selectMenusByUsername(username);

        return recursionList(menus, new ArrayList(), StringUtils.EMPTY);
    }

    @Override
    public List<String> findMenuCodesByUsername(String username) {
        List<Menu> menus = menuMapper.selectMenusByUsername(username);
        return Collections3.extractToList(menus, "code");
    }

    @Override
    public List<Menu> findAllMenus() {
        Example example = new Example(Menu.class);
        example.setOrderByClause("sort asc");
        List<Menu> menus = menuMapper.selectByExample(example);

        return recursionList(menus, new ArrayList(), StringUtils.EMPTY);
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public boolean existsMenuCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);
        return exists(menu);
    }

    /**
     * 递归找出 parentCode 下边的所有子节点
     *
     * @param from
     * @param toList
     * @param pcode
     * @return
     */
    private List<Menu> recursionList(List<Menu> from, List<Menu> toList, String pcode) {
        if (CollectionUtils.isEmpty(from)) {
            return toList;
        }

        for (int i = 0; i < from.size(); i++) {
            Menu menu = from.get(i);
            if (pcode.equals(menu.getPcode())) {
                List<Menu> leaf = new ArrayList();
                menu.setLeaf(leaf);
                toList.add(menu);
                recursionList(from, leaf, menu.getCode());
            }
        }

        return toList;
    }
}
