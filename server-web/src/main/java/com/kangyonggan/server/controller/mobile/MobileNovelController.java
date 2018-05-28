package com.kangyonggan.server.controller.mobile;

import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Params;
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
 * @author kangyonggan
 * @date 8/15/17
 */
@RestController
@RequestMapping("mobile/novel")
@Log4j2
public class MobileNovelController extends BaseController {

    @Autowired
    private NovelService novelService;

    /**
     * 搜索小说
     *
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Response search() {
        Response response = Response.getSuccessResponse();

        try {
            Params params = getRequestParams();
            params.setPageNum(1);
            params.setPageSize(100);
            List<Novel> novels = novelService.searchNovels(params);

            response.put("novels", novels);
        } catch (Exception e) {
            log.warn("搜索小说异常", e);
            response.failure("搜索小说异常");
        }

        return response;
    }

    /**
     * 查找某分类的小说
     *
     * @return
     */
    @RequestMapping(value = "category", method = RequestMethod.POST)
    public Response category() {
        Response response = Response.getSuccessResponse();

        try {
            Params params = getRequestParams();
            params.setPageSize(100);
            List<Novel> novels = novelService.searchNovels(params);

            response.put("novels", novels);
        } catch (Exception e) {
            log.warn("查找某分类的小说异常", e);
            response.failure("查找某分类的小说异常");
        }

        return response;
    }

}
