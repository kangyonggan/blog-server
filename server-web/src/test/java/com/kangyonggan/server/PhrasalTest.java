package com.kangyonggan.server;

import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Query;
import com.kangyonggan.server.model.Phrasal;
import com.kangyonggan.server.service.PhrasalService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

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
        phrasalService.importPhrasals("/Users/kyg/data/成语.txt");
    }

    /**
     * 根据字数查询
     */
    @Test
    public void testSearchByWordLen() {
        Params params = new Params();
        Query query = new Query();
        query.put("wordLen", 4);
        params.setQuery(query);
        params.setPageSize(10);
        params.setPageNum(1);
        List<Phrasal> phrasals = phrasalService.searchPhrasals(params);
        System.out.println(phrasals);
    }

    /**
     * 根据首字母查询
     */
    @Test
    public void testSearchByCapitalWord() {
        Params params = new Params();
        Query query = new Query();
        query.put("capitalWord", "A");
        params.setQuery(query);
        params.setPageSize(10);
        params.setPageNum(1);
        List<Phrasal> phrasals = phrasalService.searchPhrasals(params);
        System.out.println(phrasals);
    }

}
