package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.service.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@RestController
@RequestMapping("content/novel")
public class ContentNovelController extends BaseController {

    @Autowired
    private NovelService novelService;

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

}
