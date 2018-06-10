package com.kangyonggan.server.job;

import com.kangyonggan.server.service.SectionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2018/6/10 0010
 */
@Component
@Log4j2
public class NovelTask {

    @Autowired
    private SectionService sectionService;

    /**
     * 每天早上8点更新章节
     * cron表达式：* * * * * *（秒 分 时 日 月 星期）
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void execute() {
        log.info("引擎自动更新章节开始...");

        List<Integer> novelCodes = new ArrayList<>();
        novelCodes.add(2722);
        novelCodes.add(897);
        novelCodes.add(12564);

        sectionService.updateNovelSections(novelCodes);
        log.info("引擎自动更新章节结束!");
    }

}
