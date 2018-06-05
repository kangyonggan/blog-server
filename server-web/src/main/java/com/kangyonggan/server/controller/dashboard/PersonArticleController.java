package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import com.kangyonggan.server.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
@RestController
@RequestMapping("person/article")
public class PersonArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 我的文章列表
     *
     * @return
     */
    @GetMapping
    @PermissionMenu("article")
    public Response list() {
        Response response = Response.getSuccessResponse();
        Params params = getRequestParams();
        params.getQuery().put("createdUsername", AuthUtil.currentUsername());

        List<Article> articles = articleService.searchArticles(params);
        PageInfo pageInfo = new PageInfo<>(articles);

        response.put("pageInfo", pageInfo);

        return response;
    }

}
