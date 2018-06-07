package com.kangyonggan.server.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2018/04/02
 */
@Table(name = "tb_article")
@Data
public class Article implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 创建人
     */
    @Column(name = "created_username")
    private String createdUsername;

    /**
     * 创建人姓名
     */
    @Transient
    private String createdName;

    /**
     * 申请状态
     */
    @Column(name = "apply_status")
    private String applyStatus;

    /**
     * 审批意见
     */
    @Column(name = "reply_msg")
    private String replyMsg;

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
     * 文章内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}