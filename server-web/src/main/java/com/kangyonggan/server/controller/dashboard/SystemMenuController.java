package com.kangyonggan.server.controller.dashboard;

import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Menu;
import com.kangyonggan.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 6/4/18
 */
@RestController
@RequestMapping("system/menu")
public class SystemMenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 查找所有角色
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("menu")
    public Response list() {
        Response response = Response.getSuccessResponse();

        response.put("menus", menuService.findAllMenus());
        return response;
    }

    /**
     * 保存
     *
     * @param menu
     * @return
     */
    @PostMapping
    @PermissionMenu("menu")
    public Response save(Menu menu) {
        menuService.saveMenu(menu);
        return Response.getSuccessResponse();
    }
}
