package com.kangyonggan.server.service;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;

/**
 * @author kangyonggan
 * @since 5/17/18
 */
public interface ToolService {

    /**
     * 处理请求
     *
     * @param params
     * @param response
     * @return
     */
    void handle(Params params, Response response);
}
