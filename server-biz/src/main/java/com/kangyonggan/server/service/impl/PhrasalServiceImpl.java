package com.kangyonggan.server.service.impl;

import com.kangyonggan.server.mapper.PhrasalMapper;
import com.kangyonggan.server.model.Phrasal;
import com.kangyonggan.server.service.PhrasalService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 7/19/18
 */
@Service
@Log4j2
public class PhrasalServiceImpl extends BaseService<Phrasal> implements PhrasalService {

    @Autowired
    private PhrasalMapper phrasalMapper;

    @Override
    public void importPhrasals(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            log.error("成语导入失败，文件path={}不存在!", path);
            return;
        }

        BufferedReader reader = null;
        List<Phrasal> successList = new ArrayList<>();
        int failureCount = 0;
        try {
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                if (StringUtils.isNotEmpty(line)) {
                    Phrasal phrasal = processLine(line);
                    if (phrasal == null) {
                        failureCount++;
                    } else {
                        successList.add(phrasal);
                    }
                }
            }
        } catch (Exception e) {
            log.error("成语导入异常，解析文件失败", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        // 批量保存成语
        if (!successList.isEmpty()) {
            phrasalMapper.savePhrasals(successList);
        }

        log.info("成语导入完成，一共解析失败{}行, 解析成功{}行, 空行不算！", failureCount, successList.size());
    }

    private Phrasal processLine(String line) {
        try {
            Phrasal phrasal = new Phrasal();
            String name = line.substring(0, line.indexOf("拼音：")).trim();
            int wordLen = name.length();
            // TODO
            String type = "";
            // TODO
            String capitalWord = "";
            String spelling = line.substring(line.indexOf("拼音：") + 3, line.indexOf("释义：")).trim();
            String definition = line.substring(line.indexOf("释义：") + 3).trim();

            phrasal.setName(name);
            phrasal.setWordLen(wordLen);
            phrasal.setType(type);
            phrasal.setCapitalWord(capitalWord);
            phrasal.setSpelling(spelling);
            phrasal.setDefinition(definition);

            return phrasal;
        } catch (Exception e) {
            log.error("解析成语异常", e);
            return null;
        }
    }
}
