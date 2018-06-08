package com.kangyonggan.server.controller.admin;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.model.Section;
import com.kangyonggan.server.service.NovelService;
import com.kangyonggan.server.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@RestController
@RequestMapping("api/content/novel")
public class ContentNovelController extends BaseController {

    @Autowired
    private NovelService novelService;

    @Autowired
    private SectionService sectionService;

    /**
     * 小说列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("novel")
    public Response list() {
        Response response = Response.getSuccessResponse();
        List<Novel> novels = novelService.searchNovels(getRequestParams());

        PageInfo<Novel> pageInfo = new PageInfo<>(novels);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 章节列表
     *
     * @param novelCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/sections")
    @PermissionMenu("novel")
    public Response sections(@PathVariable("novelCode") Integer novelCode) {
        Response response = Response.getSuccessResponse();
        Novel novel = novelService.findNovelByCode(novelCode);
        List<Section> sections = sectionService.findNovelSections(getRequestParams());

        response.put("novel", novel);
        response.put("sections", sections);
        return response;
    }

    /**
     * 章节详情
     *
     * @param sectionCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/section/{sectionCode:[\\d]+}")
    @PermissionMenu("novel")
    public Response section(@PathVariable("sectionCode") Integer sectionCode) {
        Response response = Response.getSuccessResponse();
        Section section = sectionService.findSectionByCode(sectionCode);

        response.put("section", section);
        return response;
    }

    /**
     * 上一章节
     *
     * @param sectionCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/section/{sectionCode:[\\d]+}/prev")
    @PermissionMenu("novel")
    public Response prev(@PathVariable("sectionCode") Integer sectionCode) {
        Response response = Response.getSuccessResponse();
        Section section = sectionService.findPrevSectionByCode(sectionCode);

        response.put("section", section);
        return response;
    }

    /**
     * 下一章节
     *
     * @param sectionCode
     * @return
     */
    @GetMapping("{novelCode:[\\d]+}/section/{sectionCode:[\\d]+}/next")
    @PermissionMenu("novel")
    public Response next(@PathVariable("sectionCode") Integer sectionCode) {
        Response response = Response.getSuccessResponse();
        Section section = sectionService.findNextSectionByCode(sectionCode);

        response.put("section", section);
        return response;
    }

    /**
     * 恢复/禁用
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping(value = "{id:[\\d]+}/status/{status:\\b0\\b|\\b1\\b}")
    @PermissionMenu("novel")
    public Response status(@PathVariable("id") Long id, @PathVariable("status") byte status) {
        Novel novel = new Novel();
        novel.setId(id);
        novel.setStatus(status);
        novelService.updateNovel(novel);
        return Response.getSuccessResponse();
    }

    /**
     * 拉取最新章节
     *
     * @param novelCode
     * @return
     */
    @PutMapping(value = "{novelCode:[\\d]+}/pull")
    @PermissionMenu("novel")
    public Response pull(@PathVariable("novelCode") Integer novelCode) {
        sectionService.updateSections(novelCode);
        return Response.getSuccessResponse();
    }

}
