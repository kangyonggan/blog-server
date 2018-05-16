package com.kangyonggan.server.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.util.IpUtil;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Guest;
import com.kangyonggan.server.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/16/18
 */
@RestController
@RequestMapping("guest")
public class GuestController extends BaseController {

    @Autowired
    private GuestService guestService;

    /**
     * 留言列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response list() {
        Response response = Response.getSuccessResponse();
        Params params = getRequestParams();
        List<Guest> guests = guestService.findGuestsByPage(params.getPageNum(), params.getPageSize());
        PageInfo<Guest> pageInfo = new PageInfo<>(guests);
        response.put("pageInfo", pageInfo);

        return response;
    }

    /**
     * 保存言板
     *
     * @param guest
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Response save(@ModelAttribute("guest") Guest guest, BindingResult result, HttpServletRequest request) {
        Response response = Response.getSuccessResponse();

        if (result.hasErrors()) {
            response.failure("表单错误");
        } else {
            // 访问量控制
            String ip = IpUtil.getIp(request);
            guest.setIp(ip);
            guestService.saveGuest(guest);

            guestService.updateGuestIpInfo(guest.getId(), ip);

            response.success("留言成功，待审核通过之后会展示于下面留言板。");
        }

        return response;
    }

}
