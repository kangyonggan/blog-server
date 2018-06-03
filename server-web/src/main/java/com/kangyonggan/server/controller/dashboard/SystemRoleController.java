package com.kangyonggan.server.controller.dashboard;

import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @date 2018/5/27 0027
 */
@RestController
@RequestMapping("system/role")
public class SystemRoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 查找所有角色
     *
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    @PermissionMenu({"role", "user"})
    public Response all() {
        Response response = Response.getSuccessResponse();

        response.put("roles", roleService.findAllRoles());
        return response;
    }

}
