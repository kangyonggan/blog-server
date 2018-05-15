package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Section;
import com.kangyonggan.server.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@RequestMapping("section")
@RestController
public class SectionController extends BaseController {

    @Autowired
    private SectionService sectionService;

    /**
     * 章节列表
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response list() {
        Response response = Response.getSuccessResponse();
        List<Section> sections = sectionService.findAllSections(getIntegerParam("novelCode"));

        response.put("list", sections);
        return response;
    }

    /**
     * 详情
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "{code:[\\d]+}", method = RequestMethod.GET)
    public Response detail(@PathVariable("code") Integer code) {
        Response response = Response.getSuccessResponse();
        Section section = sectionService.findSectionByCode(code);

        response.put("section", section);
        return response;
    }

    /**
     * 上一章
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "{code:[\\d]+}/prev", method = RequestMethod.GET)
    public Response prev(@PathVariable("code") Integer code) {
        Response response = Response.getSuccessResponse();
        Section section = sectionService.findPrevSectionByCode(code);

        response.put("section", section);
        return response;
    }

    /**
     * 下一章
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "{code:[\\d]+}/next", method = RequestMethod.GET)
    public Response next(@PathVariable("code") Integer code) {
        Response response = Response.getSuccessResponse();
        Section section = sectionService.findNextSectionByCode(code);

        response.put("section", section);
        return response;
    }

}
