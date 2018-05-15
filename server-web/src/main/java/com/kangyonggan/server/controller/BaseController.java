package com.kangyonggan.server.controller;

import com.kangyonggan.app.util.StringUtil;
import com.kangyonggan.server.dto.Params;
import com.kangyonggan.server.dto.Query;
import com.kangyonggan.server.interceptor.ParamsInterceptor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

/**
 * 基础controller
 *
 * @author kangyonggan
 * @since 5/4/18
 */
@Log4j2
public class BaseController {

    /**
     * 获取请求参数
     *
     * @return 返回请求参数
     */
    protected Params getRequestParams() {
        Params params = new Params();

        // 分页相关参数
        params.setPageSize(getIntegerParam("limit", 10));
        int offset = getIntegerParam("offset", 0);
        params.setPageNum(offset / params.getPageSize() + 1);

        String sort = getStringParam("sort");
        params.setSort(StringUtil.camelToUnderLine(sort));
        params.setOrder(getStringParam("order", "asc"));

        // 其他查询条件
        params.setQuery(getQuery());

        return params;
    }

    /**
     * 获取查询条件
     *
     * @return 返回查询条件
     */
    protected Query getQuery() {
        Query query = new Query();
        Map<String, String[]> parameterMap = ParamsInterceptor.getParameterMap();
        for (String key : parameterMap.keySet()) {
            String[] value = parameterMap.get(key);
            if (value != null && value.length == 1) {
                query.put(key, value[0]);
            } else {
                query.put(key, value);
            }
        }

        return query;
    }

    /**
     * 获取String类型的请求参数
     *
     * @param name 参数名
     * @return 返回参数值
     */
    protected String getStringParam(String name) {
        return ParamsInterceptor.getParameter(name);
    }

    /**
     * 获取String类型的请求参数, 带默认值
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 返回参数值
     */
    protected String getStringParam(String name, String defaultValue) {
        return ParamsInterceptor.getParameter(name, defaultValue);
    }

    /**
     * 获取int类型的请求参数
     *
     * @param name 参数名
     * @return 返回int型的参数值
     */
    protected int getIntegerParam(String name) {
        return Integer.parseInt(ParamsInterceptor.getParameter(name));
    }

    /**
     * 获取int类型的请求参数, 带默认值
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return 返回int型的参数值
     */
    protected int getIntegerParam(String name, int defaultValue) {
        try {
            return Integer.parseInt(ParamsInterceptor.getParameter(name));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
