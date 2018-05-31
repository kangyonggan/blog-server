package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.User;
import lombok.extern.log4j.Log4j2;
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

    /**
     * 登录
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Response login(String username) {
        log.info(username);
        log.info(getRequestParams());
        Response response = Response.getSuccessResponse();
        if ("admin".equals(username)) {
            response.put("token", username);
        } else {
            response.failure("用户名或密码错误");
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
        log.info(getRequestParams());

        User user = new User();
        user.setUsername("admin");
        user.setName("管理员");
        response.put("user", user);

        return response;
    }

}
