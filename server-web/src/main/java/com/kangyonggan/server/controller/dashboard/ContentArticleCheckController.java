package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
@RestController
@RequestMapping("content/articleCheck")
public class ContentArticleCheckController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 我的文章列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("articleCheck")
    public Response list() {
        Response response = Response.getSuccessResponse();
        Params params = getRequestParams();

        List<Article> articles = articleService.searchArticles(params);
        PageInfo pageInfo = new PageInfo<>(articles);

        response.put("pageInfo", pageInfo);

        return response;
    }

    /**
     * 审批
     *
     * @param type
     * @param replyMsg
     * @param ids
     * @return
     */
    @PutMapping
    @PermissionMenu("articleCheck")
    public Response check(@RequestParam("type") String type, @RequestParam("replyMsg") String replyMsg,
                          @RequestParam("ids") String ids) {
        articleService.replyArticles(ids, type, replyMsg);
        return Response.getSuccessResponse();
    }
}
