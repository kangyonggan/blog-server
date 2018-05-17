package com.kangyonggan.server.service.impl;

import com.google.zxing.WriterException;
import com.kangyonggan.server.constants.Tool;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.ToolService;
import com.kangyonggan.server.util.QrCodeUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @author kangyonggan
 * @since 5/17/18
 */
@Service
@Log4j2
public class ToolServiceImpl implements ToolService {

    /**
     * 文件跟路径
     */
    @Value("${file.root.path}")
    private String fileRootPath;

    private static final String RESULT = "result";

    @Override
    public void handle(Params params, Response response) {
        try {
            String code = params.getQuery().getString("code");
            if (Tool.qr.name().equals(code)) {
                // 生成二维码
                qrHandle(params, response);
            } else {
                response.failure("该工具尚未开发");
            }
        } catch (Exception e) {
            log.error("工具调用异常", e);
            response.failure("请输入必填项");
        }
    }

    /**
     * 生成二维码
     *
     * @param params
     * @param response
     * @throws IOException
     * @throws WriterException
     */
    private void qrHandle(Params params, Response response) throws IOException, WriterException {
        String qrName = "QR" + UUID.randomUUID().toString().replaceAll("-", "") + ".png";
        String name = fileRootPath + "upload/" + qrName;
        QrCodeUtil.genQrCode(name, params.getQuery().getString("data"), Integer.parseInt(params.getQuery().getString("size")) * 100);
        log.info("二维码生成成功，路径： {}", name);
        response.put(RESULT, "upload/" + qrName);
    }
}
