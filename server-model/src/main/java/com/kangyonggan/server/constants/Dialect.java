package com.kangyonggan.server.constants;

import lombok.Getter;

/**
 * @author kangyonggan
 * @date 7/6/17
 */
public enum Dialect {

    /**
     * MySQL
     */
    MySQL("MySQL"),

    /**
     * Oracle
     */
    Oracle("Oracle"),

    /**
     * SQLServer
     */
    SQLServer("SQLServer");

    /**
     * 数据库方言
     */
    @Getter
    private final String dialect;

    Dialect(String dialect) {
        this.dialect = dialect;
    }

}

