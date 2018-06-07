package com.kangyonggan.server.controller.dashboard;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.app.util.MarkdownUtil;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.annotation.PermissionUser;
import com.kangyonggan.server.controller.BaseController;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import com.kangyonggan.server.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 保存
     *
     * @param article
     * @return
     */
    @PostMapping
    @PermissionMenu("article")
    public Response save(Article article) {
        article.setCreatedUsername(AuthUtil.currentUsername());
        articleService.saveArticle(article);
        return Response.getSuccessResponse();
    }

    /**
     * 编辑文章
     *
     * @param id
     * @return
     */
    @GetMapping("{id:[\\d]+}/edit")
    @PermissionMenu("article")
    public Response edit(@PathVariable("id") Long id) {
        Response response = Response.getSuccessResponse();
        response.put("article", articleService.getArticle(id));
        return response;
    }

    /**
     * 更新
     *
     * @param article
     * @return
     */
    @PutMapping
    @PermissionMenu({"article", "articleCheck"})
    @PermissionUser("admin")
    public Response update(Article article) {
        articleService.updateArticle(article);
        return Response.getSuccessResponse();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @PermissionMenu("article")
    @PermissionUser("admin")
    public Response update(@RequestParam("id") Long id) {
        articleService.deleteArticle(id);
        return Response.getSuccessResponse();
    }

    /**
     * 查找文章
     *
     * @param id
     * @return
     */
    @GetMapping("{id:[\\d]+}")
    @PermissionMenu({"article", "articleCheck"})
    public Response get(@PathVariable("id") Long id) {
        Response response = Response.getSuccessResponse();
        Article article = articleService.findArticleById(id);
        article.setContent(MarkdownUtil.markdownToHtml(article.getContent()));
        response.put("article", article);
        return response;
    }

}
