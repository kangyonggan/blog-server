package com.kangyonggan.server.controller.web;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.service.NovelService;
import com.kangyonggan.server.service.SectionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小说
 *
 * @author kangyonggan
 * @since 5/15/18
 */
@RestController
@RequestMapping("web/novel")
@Log4j2
public class NovelController extends BaseController {

    @Autowired
    private NovelService novelService;

    @Autowired
    private SectionService sectionService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<Novel> novels = novelService.searchNovels(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(novels);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 详情
     *
     * @param code
     * @return
     */
    @GetMapping(value = "{code:[\\d]+}")
    public Response detail(@PathVariable("code") Integer code) {
        Response response = Response.getSuccessResponse();
        Novel novel = novelService.findNovelByCode(code);

        response.put("novel", novel);
        return response;
    }

    /**
     * 拉取最新章节
     *
     * @param novelCode
     * @return
     */
    @PutMapping(value = "{novelCode:[\\d]+}/pull")
    public Response pull(@PathVariable("novelCode") Integer novelCode) {
        sectionService.updateSections(novelCode);
        return Response.getSuccessResponse();
    }
}
