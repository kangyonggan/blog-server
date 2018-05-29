package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 角色列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<Role> roles = roleService.searchRoles(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(roles);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 搜索角色
     *
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Response search() {
        Response response = Response.getSuccessResponse();

        List<Role> roles = roleService.searchRoles(getRequestParams());
        response.put("list", roles);

        return response;
    }

    /**
     * 删除
     *
     * @param codes
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response delete(@RequestParam("codes") String codes) {
        roleService.delete(codes);
        return Response.getSuccessResponse();
    }

    /**
     * 恢复
     *
     * @param codes
     * @return
     */
    @RequestMapping(value = "recovery", method = RequestMethod.POST)
    public Response recovery(@RequestParam("codes") String codes) {
        roleService.recovery(codes);
        return Response.getSuccessResponse();
    }

    /**
     * 保存
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
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
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response update(Role role) {
        roleService.updateRole(role);
        return Response.getSuccessResponse();
    }

    /**
     * 查找所有角色
     *
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Response all() {
        Response response = Response.getSuccessResponse();
        response.put("roles", roleService.findAllRoles());
        return response;
    }

}
