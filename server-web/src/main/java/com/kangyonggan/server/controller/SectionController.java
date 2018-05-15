package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.model.Section;
import com.kangyonggan.server.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
