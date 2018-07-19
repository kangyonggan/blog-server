package com.kangyonggan.server;

import com.kangyonggan.server.service.PhrasalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author kangyonggan
 * @since 7/19/18
 */
public class PhrasalTest extends AbstractTest {

    @Autowired
    private PhrasalService phrasalService;

    /**
     * 初始化成语数据库
     *
     * @throws IOException 异常
     */
    @Test
    public void testInitDb() throws IOException {
        phrasalService.importPhrasals("/Users/kyg/code/kyg2/server/server-web/src/test/resources/成语.txt");
    }

}
