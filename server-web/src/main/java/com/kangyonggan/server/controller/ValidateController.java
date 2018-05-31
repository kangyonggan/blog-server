package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.RoleService;
import com.kangyonggan.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 5/29/18
 */
@RestController
@RequestMapping("validate")
public class ValidateController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 判断是否存在用户名
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public Response user(@RequestParam("username") String username) {
        Response response = Response.getSuccessResponse();
        if (userService.existsUsername(username)) {
            response.failure();
        }
        return response;
    }

    /**
     * 判断是否存在角色代码
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "role", method = RequestMethod.GET)
    public Response role(@RequestParam("code") String code) {
        Response response = Response.getSuccessResponse();
        if (roleService.existsRoleCode(code)) {
            response.failure();
        }
        return response;
    }

}
