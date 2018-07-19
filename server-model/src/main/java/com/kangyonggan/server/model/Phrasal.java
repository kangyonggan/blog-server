package com.kangyonggan.server.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
@Table(name = "tb_phrasal")
@Data
public class Phrasal implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成语名称
     */
    private String name;

    /**
     * 字数
     */
    @Column(name = "word_len")
    private Integer wordLen;

    /**
     * 类型
     */
    private String type;

    /**
     * 首字母
     */
    @Column(name = "capital_word")
    private String capitalWord;

    /**
     * 拼音
     */
    private String spelling;

    /**
     * 释义
     */
    private String definition;

    /**
     * 状态:{0:可用, 1:禁用}
     */
    private Byte status;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}