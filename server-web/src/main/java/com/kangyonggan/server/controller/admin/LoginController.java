package com.kangyonggan.server.controller.admin;

import com.kangyonggan.server.constants.Resp;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.MenuService;
import com.kangyonggan.server.service.UserService;
import com.kangyonggan.server.util.AuthUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 5/31/18
 */
@RestController
@RequestMapping("api")
@Log4j2
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping(value = "login")
    public Response login(User user) {
        Response response = Response.getSuccessResponse();

        user = userService.login(user);
        if (user != null) {
            String token = AuthUtil.saveLoginUser(user);
            response.put("token", token);
        } else {
            response.failure(Resp.USERNAME_PASSWORD_ERROR.getRespCo(), Resp.USERNAME_PASSWORD_ERROR.getRespMsg());
        }

        return response;
    }

    /**
     * 登出
     *
     * @return
     */
    @GetMapping(value = "logout")
    public Response logout() {
        Response response = Response.getSuccessResponse();
        AuthUtil.logout();
        return response;
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    @GetMapping(value = "info")
    public Response info() {
        Response response = Response.getSuccessResponse();
        User user = userService.findUserById(AuthUtil.currentUserId());
        response.put("user", user);
        return response;
    }

    /**
     * 获取用户菜单
     *
     * @return
     */
    @GetMapping(value = "menus")
    public Response menus() {
        Response response = Response.getSuccessResponse();

        response.put("menus", menuService.findMenusByUsername(AuthUtil.currentUsername()));
        return response;
    }

}
