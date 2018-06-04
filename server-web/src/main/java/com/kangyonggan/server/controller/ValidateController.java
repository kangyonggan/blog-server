package com.kangyonggan.server.controller;

import com.kangyonggan.server.constants.Resp;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.UserService;
import com.kangyonggan.server.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kangyonggan
 * @since 5/29/18
 */
@RestController
@RequestMapping("validate")
public class ValidateController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 判断是否存在用户名
     *
     * @param username
     * @return
     */
    @GetMapping(value = "user")
    public Response user(@RequestParam("username") String username) {
        Response response = Response.getSuccessResponse();
        if (userService.existsUsername(username)) {
            response.failure();
        }
        return response;
    }

    /**
     * 校验密码是否正确
     *
     * @param password
     * @return
     */
    @PostMapping(value = "password")
    public Response password(@RequestParam("password") String password) {
        Response response = Response.getSuccessResponse();
        User user = userService.findUserByUsername(AuthUtil.currentUsername());
        if (user == null) {
            response.failure(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
            return response;
        }
        user.setPassword(password);
        user = userService.login(user);
        if (user == null) {
            response.failure(Resp.PASSWORD_ERROR.getRespCo(), Resp.PASSWORD_ERROR.getRespMsg());
        }
        return response;
    }

}
