package com.kangyonggan.server.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
@Table(name = "tb_section")
@Data
public class Section implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 章节代码
     */
    private Integer code;

    /**
     * 标题
     */
    private String title;

    /**
     * 小说代码
     */
    @Column(name = "novel_code")
    private Integer novelCode;

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

    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}