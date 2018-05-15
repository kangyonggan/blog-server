package com.kangyonggan.server.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.service.NovelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小说
 *
 * @author kangyonggan
 * @since 5/15/18
 */
@RestController
@RequestMapping("novel")
@Log4j2
public class NovelController extends BaseController {

    @Autowired
    private NovelService novelService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<Novel> novels = novelService.searchNovels(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(novels);

        response.put("pageInfo", pageInfo);
        return response;
    }

}
