package com.kangyonggan.server.controller.web;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.util.MarkdownUtil;
import com.kangyonggan.server.controller.BaseController;
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
@RequestMapping("web/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Response list() {
        Response response = Response.getSuccessResponse();

        List<Article> articles = articleService.searchArticles(getRequestParams());
        PageInfo pageInfo = new PageInfo<>(articles);

        response.put("pageInfo", pageInfo);
        return response;
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "{id:[\\d]+}")
    public Response detail(@PathVariable("id") Long id) {
        Response response = Response.getSuccessResponse();
        Article article = articleService.findArticleById(id);
        if (article != null) {
            article.setContent(MarkdownUtil.markdownToHtml(article.getContent()));
        }

        response.put("article", article);
        return response;
    }
}
