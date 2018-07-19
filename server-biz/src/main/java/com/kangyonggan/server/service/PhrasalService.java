package com.kangyonggan.server.service;

import java.io.IOException;

/**
 * @author kangyonggan
 * @since 7/19/18
 */
public interface PhrasalService {
    /**
     * 批量导入成语
     *
     * @param path 文件路径
     * @throws IOException 可能会抛异常
     */
    void importPhrasals(String path) throws IOException;
}
