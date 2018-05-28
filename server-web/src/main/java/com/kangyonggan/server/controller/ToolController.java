package com.kangyonggan.server.controller;

import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.ToolService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
     * 预处理
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Response preSubmit() {
        Response response = Response.getSuccessResponse();
        toolService.preHandle(getRequestParams(), response);

        return response;
    }

    /**
     * 提交
     *
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response submit(@RequestParam(value = "file", required = false) MultipartFile file) throws FileUploadException {
        Response response = Response.getSuccessResponse();
        toolService.handle(getRequestParams(), response, file);

        return response;
    }

}
