DROP DATABASE IF EXISTS blog_db;

CREATE DATABASE blog_db
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE blog_db;

-- ----------------------------
--  Table structure for tb_article
-- ----------------------------
DROP TABLE
IF EXISTS tb_article;

CREATE TABLE tb_article
(
  id            BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  title         VARCHAR(128)                          NOT NULL
  COMMENT '文章标题',
  content       LONGTEXT                              NOT NULL
  COMMENT '文章内容',
  is_deleted    TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '文章表';
