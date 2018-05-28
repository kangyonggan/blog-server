package com.kangyonggan.server.constants;

import lombok.Getter;

/**
 * @author kangyonggan
 * @since 2018/5/27 0027
 */
public enum YesNo {

    /**
     * 是
     */
    YES((byte) 1, "是"),

    /**
     * 否
     */
    NO((byte) 0, "否");

    /**
     * 代码
     */
    @Getter
    private byte code;

    /**
     * 名称
     */
    @Getter
    private String name;

    YesNo(byte code, String name) {
        this.code = code;
        this.name = name;
    }

}
