package com.kangyonggan.server.util;

import com.kangyonggan.server.interceptor.ParamsInterceptor;
import com.kangyonggan.server.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2018/6/2 0002
 */
public final class AuthUtil {

    /**
     * 用户登录信息
     */
    private static Map<String, Long> userIds = new HashMap<>();

    /**
     * 白名单
     */
    private static List<String> balckList = new ArrayList<>();

    private AuthUtil() {
    }

    static {
        initBlackList();
    }

    /**
     * 初始化白名单
     */
    private static void initBlackList() {
        balckList.add("/login");
        balckList.add("/logout");
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public static String saveLoginUser(User user) {
        String token = "sid" + String.valueOf(Math.random()).substring(2);
        userIds.put(token, user.getId());
        return token;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        String token = ParamsInterceptor.getRequest().getHeader("x-access-token");
        if (StringUtils.isEmpty(token)) {
            return false;
        } else if (!userIds.containsKey(token)) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否在白名单中
     *
     * @param url
     * @return
     */
    public static boolean inBlackList(String url) {
        return balckList.contains(url);
    }

    /**
     * 登出
     */
    public static void logout() {
        String token = ParamsInterceptor.getRequest().getHeader("x-access-token");
        userIds.remove(token);
    }

    /**
     * 获取当前登录的用户ID
     */
    public static Long currentUserId() {
        String token = ParamsInterceptor.getRequest().getHeader("x-access-token");
        Long id = userIds.get(token);
        return id != null ? id : 0L;
    }
}
