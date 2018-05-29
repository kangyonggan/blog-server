package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.util.Collections3;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Role;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.RoleService;
import com.kangyonggan.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @date 2018/5/27 0027
 */
@RestController
@RequestMapping("system/user")
public class SystemUserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<User> users = userService.searchUsers(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(users);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 搜索用户
     *
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Response search() {
        Response response = Response.getSuccessResponse();

        List<User> users = userService.searchUsers(getRequestParams());
        response.put("list", users);

        return response;
    }

    /**
     * 删除
     *
     * @param usernames
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response delete(@RequestParam("usernames") String usernames) {
        userService.delete(usernames);
        return Response.getSuccessResponse();
    }

    /**
     * 恢复
     *
     * @param usernames
     * @return
     */
    @RequestMapping(value = "recovery", method = RequestMethod.POST)
    public Response recovery(@RequestParam("usernames") String usernames) {
        userService.recovery(usernames);
        return Response.getSuccessResponse();
    }

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Response save(User user) {
        userService.saveUser(user);
        return Response.getSuccessResponse();
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Response update(User user) {
        userService.updateUser(user);
        return Response.getSuccessResponse();
    }

    /**
     * 修改密码
     *
     * @param user
     * @param usernames
     * @return
     */
    @RequestMapping(value = "password", method = RequestMethod.POST)
    public Response password(User user, String usernames) {
        userService.updatePassword(user, usernames);
        return Response.getSuccessResponse();
    }

    /**
     * 查找用户角色
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "{username:[\\w]+}/role", method = RequestMethod.GET)
    public Response role(@PathVariable("username") String username) {
        Response response = Response.getSuccessResponse();
        List<Role> roles = roleService.findUserRoles(username);

        response.put("roleCodes", Collections3.extractToList(roles, "code"));
        return response;
    }

    /**
     * 更新用户角色
     *
     * @param username
     * @param roleCodes
     * @return
     */
    @RequestMapping(value = "{username:[\\w]+}/role", method = RequestMethod.POST)
    public Response role(@PathVariable("username") String username,
                         @RequestParam(value = "roleCodes", required = false, defaultValue = "") String roleCodes) {
        userService.updateUserRoles(username, roleCodes);

        return Response.getSuccessResponse();
    }

}
