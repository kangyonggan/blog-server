package com.kangyonggan.server.service.impl;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.kangyonggan.server.constants.Tool;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.ToolService;
import com.kangyonggan.server.util.QrCodeUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void handle(Params params, Response response, MultipartFile file) {
        try {
            String code = params.getQuery().getString("code");
            if (Tool.qr.name().equals(code)) {
                // 生成二维码
                qrHandle(params, response);
            } else if (Tool.qr2.name().equals(code)) {
                // 二维码解析
                qr2Handle(params, response, file);
            } else {
                response.failure("该工具尚未开发");
            }
        } catch (NotFoundException e) {
            log.error("图片中没有二维码", e);
            response.failure("图片中没有二维码");
        } catch (Exception e) {
            log.error("工具调用异常", e);
            response.failure("请输入必填项");
        }
    }

    /**
     * 解析二维码
     *
     * @param params
     * @param response
     * @param file
     * @throws Exception
     */
    private void qr2Handle(Params params, Response response, MultipartFile file) throws Exception {
        String result = QrCodeUtil.decode(file.getInputStream());
        log.info("二维码解析结果：{}", result);
        response.put(RESULT, result);
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
