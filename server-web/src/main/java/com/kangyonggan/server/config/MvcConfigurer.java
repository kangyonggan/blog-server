package com.kangyonggan.server.config;

import com.kangyonggan.server.interceptor.ParamsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    /**
     * 白名单
     */
    private static List<String> balckList = new ArrayList<>();

    static {
        initBlackList();
    }

    /**
     * 初始化白名单
     */
    private static void initBlackList() {
        balckList.add("/");
        balckList.add("/login");
        balckList.add("/logout");
        balckList.add("/web/**");
        balckList.add("/admin/**");
        balckList.add("/static/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ParamsInterceptor()).addPathPatterns("/**").excludePathPatterns(balckList);
    }
}
