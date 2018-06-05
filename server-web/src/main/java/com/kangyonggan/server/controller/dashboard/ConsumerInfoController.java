package com.kangyonggan.server.controller.dashboard;

import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.User;
import com.kangyonggan.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
@RestController
@RequestMapping("consumer/info")
public class ConsumerInfoController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 更新基本信息
     *
     * @param user
     * @return
     */
    @PutMapping
    @PermissionMenu("info")
    public Response updateInfo(User user) {
        Response response = Response.getSuccessResponse();
        user.setUsername(null);
        userService.updateUser(user);

        user = userService.findUserById(user.getId());
        response.put("user", user);
        return response;
    }

}
