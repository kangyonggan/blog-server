package com.kangyonggan.server.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.constants.Resp;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.MenuService;
import com.kangyonggan.server.service.impl.MenuServiceImpl;
import com.kangyonggan.server.util.AuthUtil;
import com.kangyonggan.server.util.SpringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Log4j2
public class ParamsInterceptor extends HandlerInterceptorAdapter {

    /**
     * 存放当前请求
     */
    private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 保存当前请求
        currentRequest.set(request);

        // 判断是否登录
        boolean isLogin = AuthUtil.isLogin();
        if (!isLogin) {
            // 判断是不是在白名单中
            boolean inBlackList = AuthUtil.inBlackList(request.getRequestURI());
            if (!inBlackList) {
                Response resp = Response.getFailureResponse(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }

        // 判断是否有权限访问
        if (handler instanceof HandlerMethod && isLogin) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            PermissionMenu permissionMenu = handlerMethod.getMethodAnnotation(PermissionMenu.class);
            if (permissionMenu != null) {
                MenuService menuService = SpringUtils.getBean(MenuServiceImpl.class);
                List<String> menuCodes = menuService.findMenuCodesByUsername(AuthUtil.currentUsername());
                if (!hasPermissionMenu(menuCodes, permissionMenu.value())) {
                    Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                    writeResponse(response, resp);
                    return false;
                }
            }
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 写响应
     *
     * @param response
     * @param resp
     */
    private void writeResponse(HttpServletResponse response, Response resp) {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(resp));
            writer.flush();
        } catch (Exception e) {
            log.error("写响应异常", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
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

    /**
     * 判断是否有权限
     *
     * @param menuCodes 用户拥有的权限
     * @param menus     请求资源需要的权限
     * @return
     */
    public static boolean hasPermissionMenu(List<String> menuCodes, String[] menus) {
        for (String menuCode : menus) {
            if (menuCodes.contains(menuCode)) {
                return true;
            }
        }
        return false;
    }

}
