package com.kangyonggan.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.app.util.HtmlUtil;
import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.model.Novel;
import com.kangyonggan.server.model.Section;
import com.kangyonggan.server.service.NovelService;
import com.kangyonggan.server.service.SectionService;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Service
@Log4j2
public class SectionServiceImpl extends BaseService<Section> implements SectionService {

    /**
     * 小说更新标识
     */
    private boolean flag;

    @Autowired
    private NovelService novelService;

    @Override
    public List<Section> findAllSections(Params params) {
        Example example = new Example(Section.class);
        example.createCriteria().andEqualTo("novelCode", params.getQuery().getString("novelCode"));

        String sort = params.getSort();
        String order = params.getOrder();
        if (!StringUtil.hasEmpty(sort, order)) {
            example.setOrderByClause(sort + " " + order.toUpperCase());
        } else {
            example.setOrderByClause("code desc");
        }

        example.selectProperties("id", "code", "novelCode", "title");

        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateSections(Integer novelCode) {
        if (flag) {
            log.info("小说正在更新, 不可同时更新");
            return;
        }
        flag = true;

        try {
            pullSections(novelCode);
        } catch (Exception e) {
            log.warn("更新小说章节异常", e);
        } finally {
            flag = false;
        }
    }

    @Override
    @Log
    public Section findSectionByCode(Integer code) {
        Section section = new Section();
        section.setCode(code);

        return myMapper.selectOne(section);
    }

    @Override
    @Log
    public Section findPrevSectionByCode(Integer code) {
        Section section = findSectionByCode(code);
        if (section == null) {
            return null;
        }
        Example example = new Example(Section.class);

        example.createCriteria().andEqualTo("novelCode", section.getNovelCode()).andLessThan("code", code);

        example.setOrderByClause("code desc");

        PageHelper.startPage(1, 1);
        List<Section> sections = myMapper.selectByExample(example);
        if (sections.isEmpty()) {
            return null;
        }

        return sections.get(0);
    }

    @Override
    @Log
    public Section findNextSectionByCode(Integer code) {
        Section section = findSectionByCode(code);
        if (section == null) {
            return null;
        }
        Example example = new Example(Section.class);
        example.createCriteria().andEqualTo("novelCode", section.getNovelCode()).andGreaterThan("code", code);

        example.setOrderByClause("code asc");

        PageHelper.startPage(1, 1);
        List<Section> sections = myMapper.selectByExample(example);
        if (sections.isEmpty()) {
            return null;
        }

        return sections.get(0);
    }

    @Override
    public List<Section> findNext100Sections(Integer code) {
        Section section = findSectionByCode(code);
        if (section == null) {
            return new ArrayList<>();
        }
        Example example = new Example(Section.class);
        example.createCriteria().andEqualTo("novelCode", section.getNovelCode()).andGreaterThanOrEqualTo("code", code);

        example.setOrderByClause("code asc");

        PageHelper.startPage(1, 100);
        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    public Section findFirstSection(int novelCode) {
        Example example = new Example(Section.class);

        example.createCriteria().andEqualTo("novelCode", novelCode);

        example.setOrderByClause("code asc");
        PageHelper.startPage(1, 1);
        List<Section> sections = myMapper.selectByExample(example);

        return sections.isEmpty() ? null : sections.get(0);
    }

    /**
     * 拉取小说章节
     *
     * @param novelCode
     */
    private void pullSections(Integer novelCode) {
        Novel novel = novelService.findNovelByCode(novelCode);

        if (novel == null) {
            log.info("小说{}不存在，退出章节更新", novelCode);
            return;
        }

        try {
            // 最新章节
            Section lastSection = findLastSectionByNovelCode(novelCode);
            Document bookDoc = HtmlUtil.parseUrl(NovelService.BI_QU_GE_URL + "book/" + novelCode);
            Elements elements = bookDoc.select("#list dl dd a");

            int startNum = 0;
            if (lastSection != null) {
                for (int i = 0; i < elements.size(); i++) {
                    Element element = elements.get(i);
                    String scode = element.attr("href");
                    scode = scode.substring(scode.lastIndexOf("/") + 1, scode.lastIndexOf("."));
                    if (lastSection.getCode() == Integer.parseInt(scode)) {
                        startNum = i + 1;
                        break;
                    }
                }
            }

            log.info("从第{}章节开始更新", startNum);
            for (int i = startNum; i < elements.size(); i++) {
                Element element = elements.get(i);
                String scode = element.attr("href");
                scode = scode.substring(scode.lastIndexOf("/") + 1, scode.lastIndexOf("."));
                parseSection(novelCode, Integer.parseInt(scode));
            }
        } catch (Exception e) {
            log.warn("抓取章节异常", e);
            return;
        }
    }

    /**
     * 查找最后章节
     *
     * @param code
     * @return
     */
    @Log
    private Section findLastSectionByNovelCode(Integer code) {
        Example example = new Example(Section.class);
        example.createCriteria().andEqualTo("novelCode", code);
        example.setOrderByClause("code desc");

        example.selectProperties("id", "code", "title", "novelCode");

        PageHelper.startPage(1, 1);
        List<Section> sections = myMapper.selectByExample(example);
        if (sections.isEmpty()) {
            return null;
        }
        return sections.get(0);
    }

    /**
     * 解析章节
     *
     * @param novelCode
     * @param sectionCode
     */
    private void parseSection(int novelCode, int sectionCode) {
        Document doc = HtmlUtil.parseUrl(NovelService.BI_QU_GE_URL + "/book/" + novelCode + "/" + sectionCode + ".html");

        String title = doc.select(".bookname h1").html().trim();
        String content = doc.select("#content").html();
        content = content.replaceAll("……", "...");
        content = content.replaceAll("———", "");

        Section section = new Section();
        section.setNovelCode(novelCode);
        section.setCode(sectionCode);
        section.setTitle(title);
        section.setContent(content);

        log.info("章节【{}】保存成功", section.getTitle());
        myMapper.insertSelective(section);
    }
}
