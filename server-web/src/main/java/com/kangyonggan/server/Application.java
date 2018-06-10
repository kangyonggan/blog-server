package com.kangyonggan.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author kangyonggan
 * @since 5/15/18
 */
@SpringBootApplication
@MapperScan("com.kangyonggan.server.mapper")
@PropertySource("app.properties")
@EnableScheduling
public class Application {

    /**
     * 启动
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
