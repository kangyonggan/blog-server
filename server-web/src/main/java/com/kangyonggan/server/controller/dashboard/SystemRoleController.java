package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("all")
    @PermissionMenu({"role", "user"})
    public Response all() {
        Response response = Response.getSuccessResponse();

        response.put("roles", roleService.findAllRoles());
        return response;
    }


    /**
     * 角色列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("role")
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<Role> roles = roleService.searchRoles(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(roles);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 恢复/禁用
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping(value = "{id:[\\d]+}/status/{status:\\b0\\b|\\b1\\b}")
    @PermissionMenu("role")
    public Response status(@PathVariable("id") Long id, @PathVariable("status") byte status) {
        Role role = new Role();
        role.setId(id);
        role.setStatus(status);
        roleService.updateRole(role);
        return Response.getSuccessResponse();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id:[\\d]+}")
    @PermissionMenu("role")
    public Response delete(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return Response.getSuccessResponse();
    }

    /**
     * 保存
     *
     * @param role
     * @return
     */
    @PostMapping
    @PermissionMenu("role")
    public Response save(Role role) {
        roleService.saveRole(role);
        return Response.getSuccessResponse();
    }

    /**
     * 更新
     *
     * @param role
     * @return
     */
    @PutMapping
    @PermissionMenu("role")
    public Response update(Role role) {
        Role r = new Role();
        r.setId(role.getId());
        r.setName(role.getName());
        roleService.updateRole(r);
        return Response.getSuccessResponse();
    }

}
