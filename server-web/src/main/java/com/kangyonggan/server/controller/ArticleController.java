package com.kangyonggan.server.controller;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.util.MarkdownUtil;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章
 *
 * @author kangyonggan
 * @since 5/15/18
 */
@RestController
@RequestMapping("article")
@Log4j2
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
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
    @RequestMapping(value = "{id:[\\d]+}", method = RequestMethod.GET)
    public Response detail(@PathVariable("id") Long id) {
        Response response = Response.getSuccessResponse();
        Article article = articleService.findArticleById(id);
        article.setContent(MarkdownUtil.markdownToHtml(article.getContent()));

        response.put("article", article);
        return response;
    }

}
