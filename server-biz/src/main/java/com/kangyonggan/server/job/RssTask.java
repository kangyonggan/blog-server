package com.kangyonggan.server.job;

import com.kangyonggan.server.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author kangyonggan
 * @since 2018/6/10 0010
 */
@Component
@Log4j2
public class RssTask {

    @Autowired
    private ArticleService articleService;

    /**
     * 每天凌晨1点更新RSS
     * cron表达式：* * * * * *（秒 分 时 日 月 星期）
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void execute() {
        log.info("RSS开始生成...");

        articleService.genRss("admin");
        log.info("RSS生成结束!");
    }

}
