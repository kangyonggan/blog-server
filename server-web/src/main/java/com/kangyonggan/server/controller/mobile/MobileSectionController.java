package com.kangyonggan.server.controller.mobile;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Section;
import com.kangyonggan.server.service.SectionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @date 8/24/17
 */
@RestController
@RequestMapping("mobile/section")
@Log4j2
public class MobileSectionController {

    @Autowired
    private SectionService sectionService;

    /**
     * 查找指定章节
     *
     * @param novelCode
     * @param code
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response section(@RequestParam("novelCode") int novelCode, @RequestParam("code") int code) {
        Response response = Response.getSuccessResponse();

        try {
            Section section = sectionService.findSectionByCode(code);

            if (section == null) {
                response.failure("不存在的章节");
                return response;
            }

            response.put("section", section);
        } catch (Exception e) {
            log.warn("查找小说章节异常", e);
            response.failure("查找小说章节异常");
        }

        return response;
    }

    /**
     * 查找下一个章节
     *
     * @param novelCode
     * @param code
     * @return
     */
    @RequestMapping(value = "next", method = RequestMethod.POST)
    public Response nextSection(@RequestParam("novelCode") int novelCode, @RequestParam("code") int code) {
        Response response = Response.getSuccessResponse();

        try {
            Section nextSection = sectionService.findNextSectionByCode(code);

            if (nextSection == null) {
                response.failure("不存在下一个章节");
                return response;
            }

            response.put("section", nextSection);
        } catch (Exception e) {
            log.warn("查找小说章节异常", e);
            response.failure("查找小说章节异常");
        }

        return response;
    }

    /**
     * 查找上一个章节
     *
     * @param novelCode
     * @param code
     * @return
     */
    @RequestMapping(value = "prev", method = RequestMethod.POST)
    public Response prevSection(@RequestParam("novelCode") int novelCode, @RequestParam("code") int code) {
        Response response = Response.getSuccessResponse();

        try {
            Section nextSection = sectionService.findPrevSectionByCode(code);

            if (nextSection == null) {
                response.failure("不存在上一个章节");
                return response;
            }

            response.put("section", nextSection);
        } catch (Exception e) {
            log.warn("查找小说章节异常", e);
            response.failure("查找小说章节异常");
        }

        return response;
    }

    /**
     * 查找第一个章节
     *
     * @param novelCode
     * @return
     */
    @RequestMapping(value = "first", method = RequestMethod.POST)
    public Response lastSection(@RequestParam("novelCode") int novelCode) {
        Response response = Response.getSuccessResponse();

        try {
            Section section = sectionService.findFirstSection(novelCode);

            if (section == null) {
                response.failure("小说章节尚未拉取，可以联系康永敢优先拉取此小说");
                return response;
            }

            response.put("section", section);
        } catch (Exception e) {
            log.warn("查找小说第一个章节异常", e);
            response.failure("查找小说第一个章节异常");
        }

        return response;
    }

    /**
     * 查找小说后面100章
     *
     * @param novelCode
     * @param code
     * @return
     */
    @RequestMapping(value = "cache", method = RequestMethod.POST)
    public Response sectionCache(@RequestParam("novelCode") int novelCode, @RequestParam("code") int code) {
        Response response = Response.getSuccessResponse();

        try {
            List<Section> sections = sectionService.findNext100Sections(code);
            response.put("sections", sections);
        } catch (Exception e) {
            log.warn("查找小说后面100章异常", e);
            response.failure("查找小说后面100章异常");
        }

        return response;
    }

    /**
     * 查找小说全部章节
     *
     * @param novelCode
     * @return
     */
    @RequestMapping(value = "all", method = RequestMethod.POST)
    public Response sections(@RequestParam("novelCode") int novelCode) {
        Response response = Response.getSuccessResponse();

        try {
            List<Section> sections = sectionService.findAllSections(novelCode);

            response.put("sections", sections);
        } catch (Exception e) {
            log.warn("查找小说全部章节异常", e);
            response.failure("查找小说全部章节异常");
        }

        return response;
    }
}
