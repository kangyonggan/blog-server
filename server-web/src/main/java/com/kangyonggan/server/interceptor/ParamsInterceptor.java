package com.kangyonggan.server.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.kangyonggan.app.util.Collections3;
import com.kangyonggan.server.annotation.PermissionMenu;
import com.kangyonggan.server.annotation.PermissionRole;
import com.kangyonggan.server.annotation.PermissionUser;
import com.kangyonggan.server.constants.Resp;
import com.kangyonggan.server.dto.Response;
import com.kangyonggan.server.service.MenuService;
import com.kangyonggan.server.service.RoleService;
import com.kangyonggan.server.service.impl.MenuServiceImpl;
import com.kangyonggan.server.util.AuthUtil;
import com.kangyonggan.server.util.SpringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        log.info("url:{}", request.getRequestURI());
        // 保存当前请求
        currentRequest.set(request);

        // 放过前端请求
        if (request.getRequestURI().startsWith("/web/")) {
            return super.preHandle(request, response, handler);
        }

        // 判断是否登录
        boolean isLogin = AuthUtil.isLogin();
        if (!isLogin) {
            boolean isCommonReq = !(
                    (request.getHeader("accept") != null && request.getHeader("accept").contains("application/json"))
                    || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest")));
            if (isCommonReq) {
                log.info("重定向到首页");
                // 乱七八糟的请求全部重定向到首页
                response.sendRedirect("/");
                return false;
            } else {
                log.info("登录失效");
                // 9998: 登录失效
                Response resp = Response.getFailureResponse(Resp.INVALID_LOGIN.getRespCo(), Resp.INVALID_LOGIN.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }

        // 判断是否有权限访问
        if (handler instanceof HandlerMethod && isLogin) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (!validMenu(response, handlerMethod)) {
                return false;
            }
            if (!validRole(response, handlerMethod)) {
                return false;
            }
            if (!validUser(response, handlerMethod)) {
                return false;
            }
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 校验菜单权限
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validMenu(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionMenu permissionMenu = handlerMethod.getMethodAnnotation(PermissionMenu.class);
        if (permissionMenu != null) {
            MenuService menuService = SpringUtils.getBean(MenuServiceImpl.class);
            List<String> menuCodes = menuService.findMenuCodesByUsername(AuthUtil.currentUsername());
            if (!hasPermission(menuCodes, permissionMenu.value())) {
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验角色权限
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validRole(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionRole permissionRole = handlerMethod.getMethodAnnotation(PermissionRole.class);
        if (permissionRole != null) {
            RoleService roleService = SpringUtils.getBean(RoleService.class);
            List<String> roleCodes = Collections3.extractToList(roleService.findUserRoles(AuthUtil.currentUsername()), "code");
            if (!hasPermission(roleCodes, permissionRole.value())) {
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验用户权限
     *
     * @param response
     * @param handlerMethod
     * @return
     */
    private boolean validUser(HttpServletResponse response, HandlerMethod handlerMethod) {
        PermissionUser permissionUser = handlerMethod.getMethodAnnotation(PermissionUser.class);
        if (permissionUser != null) {
            List<String> username = new ArrayList<>();
            username.add(AuthUtil.currentUsername());
            if (!hasPermission(username, permissionUser.value())) {
                Response resp = Response.getFailureResponse(Resp.PERMISSION_DENIED.getRespCo(), Resp.PERMISSION_DENIED.getRespMsg());
                writeResponse(response, resp);
                return false;
            }
        }
        return true;
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
     * @param ownCodes 用户拥有的权限
     * @param reqCodes 请求资源需要的权限
     * @return
     */
    public static boolean hasPermission(List<String> ownCodes, String[] reqCodes) {
        for (String code : reqCodes) {
            if (ownCodes.contains(code)) {
                return true;
            }
        }
        return false;
    }

}
