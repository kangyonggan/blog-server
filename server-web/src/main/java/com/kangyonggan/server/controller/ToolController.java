package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.ToolService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangyonggan
 * @since 5/17/18
 */
@RestController
@RequestMapping("tool")
public class ToolController extends BaseController {

    @Autowired
    private ToolService toolService;

    /**
     * 提交
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response submit() throws FileUploadException {
        Response response = Response.getSuccessResponse();
        toolService.handle(getRequestParams(), response);

        return response;
    }

}
