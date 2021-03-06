package com.kangyonggan.server.controller.admin;

import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Menu;
import com.kangyonggan.server.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangyonggan
 * @since 6/4/18
 */
@RestController
@RequestMapping("api/system/menu")
public class SystemMenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 查找所有菜单
     *
     * @return
     */
    @GetMapping
    @PermissionMenu({"menu", "role"})
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

    /**
     * 删除
     *
     * @param code
     * @return
     */
    @DeleteMapping
    @PermissionMenu("menu")
    public Response delete(@RequestParam("code") String code) {
        menuService.deleteMenu(code);
        return Response.getSuccessResponse();
    }

    /**
     * 更新
     *
     * @param menu
     * @return
     */
    @PutMapping
    @PermissionMenu("menu")
    public Response update(Menu menu) {
        menuService.updateMenu(menu);
        return Response.getSuccessResponse();
    }
}
