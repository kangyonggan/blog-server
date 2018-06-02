package com.kangyonggan.server.util;

import com.kangyonggan.server.model.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
    private static Map<String, User> users = new HashMap<>();

    /**
     * 白名单
     */
    private static List<String> balckList = new ArrayList<>();

    private AuthUtil() {}

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
    public static String login(User user) {
        String token = "sid" + String.valueOf(Math.random()).substring(2);
        users.put(token, user);
        return token;
    }

    /**
     * 判断是否登录
     *
     * @param request
     * @return
     */
    public static boolean isLogin(HttpServletRequest request) {
        String token = request.getHeader("x-access-token");
        if (StringUtils.isEmpty(token)) {
            return false;
        } else if (!users.containsKey(token)) {
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
}
