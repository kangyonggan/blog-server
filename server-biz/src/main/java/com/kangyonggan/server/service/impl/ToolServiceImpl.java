package com.kangyonggan.server.service.impl;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.kangyonggan.server.constants.Tool;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Query;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.ToolService;
import com.kangyonggan.server.util.CalendarUtil;
import com.kangyonggan.server.util.DestinyUtil;
import com.kangyonggan.server.util.QrCodeUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            } else if (Tool.bazi.name().equals(code)) {
                baziHandle(params, response);
            } else {
                response.failure("该工具尚未开发");
            }
        } catch (NotFoundException e) {
            log.error("图片中没有二维码", e);
            response.failure("图片中没有二维码");
        } catch (Exception e) {
            log.error("工具调用异常", e);
            response.failure("输入参数错误");
        }
    }

    /**
     * 八字五行
     *
     * @param params
     * @param response
     * @throws Exception
     */
    private void baziHandle(Params params, Response response) throws Exception {
        Query query = params.getQuery();
        String bazi;
        String yinli;
        String yangli;
        String lunarFlag = "0";
        String br = "</br>";
        if (lunarFlag.equals(query.getString("lunar"))) {
            bazi = DestinyUtil.getEightWord4Lunar(query.getInteger("year"),
                    query.getInteger("month"), query.getInteger("day"), query.getInteger("hour"));
            yinli = LocalDate.of(query.getInteger("year"), query.getInteger("month"), query.getInteger("day")).format(DateTimeFormatter.BASIC_ISO_DATE);
            yangli = CalendarUtil.lunarToSolar(yinli);
        } else {
            bazi = DestinyUtil.getEightWord(query.getInteger("year"), query.getInteger("month"), query.getInteger("day"), query.getInteger("hour"));
            yangli = LocalDate.of(query.getInteger("year"), query.getInteger("month"), query.getInteger("day")).format(DateTimeFormatter.BASIC_ISO_DATE);
            yinli = CalendarUtil.solarToLunar(yangli);
        }
        String wuxing = DestinyUtil.getWuXing(bazi);
        String shengxiao = DestinyUtil.getShengXiao(Integer.parseInt(yangli.substring(0, 4)));
        String yunshi = DestinyUtil.getYunShi(wuxing.substring(4, 5), Integer.parseInt(yangli.substring(4, 6)));

        StringBuilder result = new StringBuilder();
        result.append("阴历出生年月：").append(yinli).append(br);
        result.append("阳历出生年月：").append(yangli).append(br);
        result.append("生辰八字：").append(bazi).append(br);
        result.append("五行：").append(wuxing).append(br);
        result.append(DestinyUtil.wuxing(wuxing));
        result.append("生肖：").append(shengxiao).append(br);
        result.append("运势：").append(yunshi).append(br);

        response.put(RESULT, result.toString());
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
