package com.kangyonggan.server.service.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.kangyonggan.app.util.CompressorUtil;
import com.kangyonggan.app.util.GsonUtil;
import com.kangyonggan.app.util.XmlUtil;
import com.kangyonggan.server.constants.Dialect;
import com.kangyonggan.server.constants.Tool;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Query;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.ToolService;
import com.kangyonggan.server.util.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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
    private static final String BR = "</br>";

    @Override
    public void preHandle(Params params, Response response) {
        String code = params.getQuery().getString("code");
        if (Tool.gencard.name().equals(code)) {
            // 生成身份证
            response.put("cityCodes", IDCardUtil.getCityCodes());
        }
    }

    @Override
    public void handle(Params params, Response response, MultipartFile file) {
        try {
            String code = params.getQuery().getString("code");
            String data = params.getQuery().getString("data");
            if (Tool.qr.name().equals(code)) {
                // 生成二维码
                qrHandle(params, response);
            } else if (Tool.qr2.name().equals(code)) {
                // 二维码解析
                qr2Handle(params, response, file);
            } else if (Tool.bazi.name().equals(code)) {
                // 八字、五行
                baziHandle(params, response);
            } else if (Tool.xml.name().equals(code)) {
                // xml格式化
                response.put(RESULT, XmlUtil.format(params.getQuery().getString("data")));
            } else if (Tool.idcard.name().equals(code)) {
                // 身份证查询
                idcardHandle(params, response);
            } else if (Tool.gencard.name().equals(code)) {
                // 生成身份证
                gencardHandle(params, response);
            } else if (Tool.sql.name().equals(code)) {
                // 格式化SQL
                sqlHandle(params, response);
            } else if (Tool.json.name().equals(code)) {
                // 格式化JSON
                response.put(RESULT, GsonUtil.format(data));
            } else if (Tool.js.name().equals(code)) {
                // 压缩JS
                jsHandle(params, response);
            } else if (Tool.css.name().equals(code)) {
                // 压缩CSS
                response.put(RESULT, CompressorUtil.compressCSS(data));
            } else if (Tool.charset.name().equals(code)) {
                // 编码转换
                response.put(RESULT, CharsetUtil.convert(data, params.getQuery().getString("charset")));
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
     * JS压缩
     *
     * @param params
     * @param response
     * @throws Exception
     */
    private void jsHandle(Params params, Response response) throws Exception {
        Map<String, String> map = CompressorUtil.compressJS(params.getQuery().getString("data"));
        StringBuilder result = new StringBuilder();
        if (StringUtils.isNotEmpty(map.get("result"))) {
            result.append("压缩结果：").append(map.get("result")).append("\r\n\r\n");
        }
        if (StringUtils.isNotEmpty(map.get("warningMsg"))) {
            result.append("警告：").append(map.get("warningMsg")).append("\r\n\r\n");
        }
        if (StringUtils.isNotEmpty(map.get("errorMsg"))) {
            result.append("错误：").append(map.get("errorMsg")).append("\r\n\r\n");
        }

        response.put(RESULT, result.toString());
    }

    /**
     * 格式化SQL
     *
     * @param params
     * @param response
     */
    private void sqlHandle(Params params, Response response) {
        String dialect = params.getQuery().getString("dialect");
        String data = params.getQuery().getString("data");
        String result = "不支持的方言";
        if (Dialect.MySQL.getDialect().equals(dialect)) {
            result = SQLUtils.formatMySql(data);
        } else if (Dialect.Oracle.getDialect().equals(dialect)) {
            result = SQLUtils.formatOracle(data);
        } else if (Dialect.SQLServer.getDialect().equals(dialect)) {
            result = SQLUtils.formatSQLServer(data);
        }

        response.put(RESULT, result);
    }

    /**
     * 生成身份证
     *
     * @param params
     * @param response
     */
    private void gencardHandle(Params params, Response response) {
        Query query = params.getQuery();
        List<String> list = IDCardUtil.genIdCard(query.getString("prov"), query.getInteger("startAge", 20), query.getInteger("endAge", 60), query.getString("sec"), query.getInteger("len", -1), query.getInteger("count", 10));
        StringBuilder result = new StringBuilder();
        for (String card : list) {
            result.append(card).append(BR);
        }

        response.put(RESULT, result.toString());
    }

    /**
     * 身份证查询
     *
     * @param params
     * @param response
     */
    private void idcardHandle(Params params, Response response) {
        String data = params.getQuery().getString("data");
        String[] res = IDCardUtil.isIdCard(data);
        String flat = "0";
        StringBuilder result = new StringBuilder();
        if (flat.equals(res[0])) {
            String year = IDCardUtil.getYearFromIdCard(data);
            result.append("省份：").append(IDCardUtil.getProvinceFromIdCard(data)).append(BR);
            result.append("年龄：").append(IDCardUtil.getAgeFromIdCard(data)).append("周岁").append(BR);
            result.append("生日：").append(IDCardUtil.getYearFromIdCard(data)).append("-")
                    .append(IDCardUtil.getMonthFromIdCard(data)).append("-").append(IDCardUtil.getDayFromIdCard(data)).append(BR);
            result.append("性别：").append(IDCardUtil.getSexFromIdCard(data) == 0 ? "男" : "女").append(BR);
            result.append("地区：").append(IDCardUtil.getAreaFromIdCard(data)).append(BR);
            result.append("生肖：").append(DestinyUtil.getShengXiao(Integer.parseInt(year))).append(BR);
            result.append("出生年份：").append(DestinyUtil.getYearColumn(Integer.parseInt(year))).append("年").append(BR);
            String month = IDCardUtil.getMonthFromIdCard(data);
            String day = IDCardUtil.getDayFromIdCard(data);
            String tianGan = DestinyUtil.getDayColumn(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)).substring(0, 1);
            result.append("运势：").append(DestinyUtil.getYunShi(DestinyUtil.getTianGanWuXing(tianGan), Integer.parseInt(month))).append(BR);

            if (data.length() == 15) {
                result.append("转成18位：").append(IDCardUtil.convert15To18(data)).append(BR);
            } else {
                result.append("转成15位：").append(IDCardUtil.convert18To15(data)).append(BR);
            }
        } else {
            result.append("您输入的身份证号码无效");
        }

        response.put(RESULT, result.toString());
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
        result.append("阴历出生年月：").append(yinli).append(BR);
        result.append("阳历出生年月：").append(yangli).append(BR);
        result.append("生辰八字：").append(bazi).append(BR);
        result.append("五行：").append(wuxing).append(BR);
        result.append(DestinyUtil.wuxing(wuxing));
        result.append("生肖：").append(shengxiao).append(BR);
        result.append("运势：").append(yunshi).append(BR);

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
