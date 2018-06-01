package com.kangyonggan.server.constants;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author kangyonggan
 * @date 5/5/17
 */
public enum Status {

    /**
     * 可用
     */
    ENABLE((byte) 0, "可用"),

    /**
     * 禁用
     */
    DISABLE((byte) 1, "禁用");

    /**
     * 状态代码
     */
    @Getter
    private final byte code;

    /**
     * 状态名称
     */
    @Getter
    private final String name;

    Status(byte code, String name) {
        this.code = code;
        this.name = name;
    }

}
