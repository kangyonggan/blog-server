package com.kangyonggan.server.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
public class ParamsInterceptor extends HandlerInterceptorAdapter {

    /**
     * 存放当前请求
     */
    private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 保存当前请求
        currentRequest.set(request);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 从本地线程中移除请求
        currentRequest.remove();
    }

    /**
     * 获取当前请求
     *
     * @return 当前请求
     */
    public static HttpServletRequest getRequest() {
        return currentRequest.get();
    }

    /**
     * 获取所有请求参数
     *
     * @return 返回所有的参数
     */
    public static Map<String, String[]> getParameterMap() {
        return getRequest().getParameterMap();
    }

    /**
     * 获取请求参数
     *
     * @param name 参数名称
     * @return 返回参数的值
     */
    public static String getParameter(String name) {
        return getParameter(name, null);
    }

    /**
     * 获取请求参数
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 返回参数的值
     */
    public static String getParameter(String name, String defaultValue) {
        String value = currentRequest.get().getParameter(name);
        return value == null ? defaultValue : value;
    }

}
