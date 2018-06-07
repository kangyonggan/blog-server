package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author kangyonggan
 * @since 6/7/18
 */
public interface ToolService {
    /**
     * 预处理请求
     *
     * @param params
     * @param response
     */
    void preHandle(Params params, Response response);

    /**
     * 处理请求
     *
     * @param params
     * @param response
     * @param file
     * @return
     */
    void handle(Params params, Response response, MultipartFile file);
}
