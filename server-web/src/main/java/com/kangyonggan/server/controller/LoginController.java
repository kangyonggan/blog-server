package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.UserService;
import com.kangyonggan.server.util.AuthUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 5/31/18
 */
@RestController("/")
@Log4j2
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response login(User user) {
        Response response = Response.getSuccessResponse();

        user = userService.login(user);
        if (user != null) {
            String token = AuthUtil.saveLoginUser(user);
            response.put("token", token);
        } else {
            response.failure("用户名或密码错误！");
        }

        return response;
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public Response logout() {
        log.info(getRequestParams());
        Response response = Response.getSuccessResponse();
        AuthUtil.logout();
        return response;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping(value = "user/info", method = RequestMethod.GET)
    public Response info() {
        Response response = Response.getSuccessResponse();
        response.put("user", userService.findUserById(AuthUtil.currentUserId()));
        return response;
    }

}
