package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.MarkdownUtil;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.constants.ApplyStatus;
import com.kangyonggan.server.constants.Status;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.mapper.ArticleMapper;
import com.kangyonggan.server.model.Article;
import com.kangyonggan.server.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @since 6/5/18
 */
@Service
@Log4j2
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Value("${file.root.path}")
    private String fileRootPath;

    @Override
    public List<Article> searchArticles(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        return articleMapper.searchArticles(params.getQuery());
    }

    @Override
    @Log
    public void saveArticle(Article article) {
        article.setApplyStatus(ApplyStatus.APPLY.getCode());
        myMapper.insertSelective(article);
    }

    @Override
    @Log
    public Article findArticleById(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setApplyStatus(ApplyStatus.COMPLETE.getCode());
        article.setStatus(Status.ENABLE.getCode());
        return myMapper.selectOne(article);
    }

    @Override
    @Log
    public void updateArticle(Article article) {
        article.setApplyStatus(ApplyStatus.APPLY.getCode());
        myMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    @Log
    public void deleteArticle(Long id) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(Status.DISABLE.getCode());

        myMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    @Log
    public Article getArticle(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    @Log
    public void replyArticles(String ids, String type, String replyMsg) {
        Article article = new Article();
        article.setReplyMsg(replyMsg);
        article.setApplyStatus(type);

        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("applyStatus", ApplyStatus.APPLY.getCode())
                .andIn("id", Arrays.asList(ids.split(",")));

        myMapper.updateByExampleSelective(article, example);

    }

    @Override
    public void genRss(String username) {
        Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("status", Status.ENABLE.getCode())
                .andEqualTo("applyStatus", ApplyStatus.COMPLETE.getCode())
                .andEqualTo("createdUsername", username);
        example.setOrderByClause("id desc");
        List<Article> articles = myMapper.selectByExample(example);

        StringBuilder rss = new StringBuilder("<feed xmlns=\"http://www.w3.org/2005/Atom\"><title>");
        rss.append("康永敢的博客").append("</title>");
        rss.append("<link href=\"/rss/blog.xml\" rel=\"self\"/>").append("<link href=\"https://www.kangyonggan.com/\"/>");
        rss.append("<updated>").append(formatXmlDate(new Date())).append("</updated>");
        rss.append("<id>https://www.kangyonggan.com/</id>");
        rss.append("<author><name>").append("康永敢").append("</name></author>");

        for (Article article : articles) {
            rss.append("<entry><title>").append(article.getTitle()).append("</title>");
            rss.append("<link href=\"https://www.kangyonggan.com/#/article/").append(article.getId()).append("\"/>");
            rss.append("<id>https://www.kangyonggan.com/#/article/").append(article.getId()).append("</id>");
            rss.append("<published>").append(formatXmlDate(article.getCreatedTime())).append("</published>");
            rss.append("<updated>").append(formatXmlDate(article.getUpdatedTime())).append("</updated>");
            rss.append("<content type=\"html\"><![CDATA[").append(MarkdownUtil.markdownToHtml(article.getContent())).append("]]></content>");
            rss.append("</entry>");
        }

        rss.append("</feed>");

        File file = new File(fileRootPath + "upload/rss.xml");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(rss.toString());
            writer.flush();
        } catch (Exception e) {
            log.error("生成博客rss异常, 文件路径：" + fileRootPath + "upload/rss.xml", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    log.error("写rss后关闭输入流异常", e);
                }
            }
        }
    }

    @Override
    public Article findPrevArticle(Long id) {
        Example example = new Example(Article.class);

        example.createCriteria().andEqualTo("status", Status.ENABLE.getCode()).andLessThan("id", id);
        example.setOrderByClause("id desc");
        example.selectProperties("id", "title");

        PageHelper.startPage(1, 1);
        List<Article> articles = myMapper.selectByExample(example);
        if (articles.isEmpty()) {
            return null;
        }

        return articles.get(0);
    }

    @Override
    public Article findNextArticle(Long id) {
        Example example = new Example(Article.class);

        example.createCriteria().andEqualTo("status", Status.ENABLE.getCode()).andGreaterThan("id", id);
        example.setOrderByClause("id asc");
        example.selectProperties("id", "title");

        PageHelper.startPage(1, 1);
        List<Article> articles = myMapper.selectByExample(example);
        if (articles.isEmpty()) {
            return null;
        }

        return articles.get(0);
    }

    private String formatXmlDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date) + "T" + new SimpleDateFormat("HH:mm:ss.SSS").format(date) + "Z";
    }
}
