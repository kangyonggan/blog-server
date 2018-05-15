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

-- ----------------------------
--  Table structure for tb_category
-- ----------------------------
DROP TABLE
IF EXISTS tb_category;

CREATE TABLE tb_category
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '栏目代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '栏目名称',
  type         VARCHAR(16)                           NOT NULL
  COMMENT '栏目类型',
  sort         INT(11)                               NOT NULL                    DEFAULT 0
  COMMENT '栏目排序(从0开始)',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '栏目表';
CREATE UNIQUE INDEX code_type_UNIQUE
  ON tb_category (code, type);
CREATE INDEX id_type
  ON tb_category (type);

-- ----------------------------
--  Table structure for tb_novel
-- ----------------------------
DROP TABLE
IF EXISTS tb_novel;

CREATE TABLE tb_novel
(
  id            BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  name          VARCHAR(32)                           NOT NULL
  COMMENT '书名',
  author        VARCHAR(32)                           NOT NULL
  COMMENT '作者',
  category_code VARCHAR(32)                           NOT NULL
  COMMENT '分类代码',
  pic_url       VARCHAR(256)                          NOT NULL                    DEFAULT '/upload/default-book.png'
  COMMENT '封面图片地址',
  code          INT(11)                               NOT NULL
  COMMENT '书籍代码',
  descp         VARCHAR(2048)                         NOT NULL
  COMMENT '描述',
  is_deleted    TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time  TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '书籍表';
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_novel (code);
CREATE INDEX ix_category_code
  ON tb_novel (category_code);


INSERT INTO tb_category
(code, name, type, sort)
VALUES
  ('xuanhuan', '玄幻', 'NOVEL', 0),
  ('dushi', '都市', 'NOVEL', 1),
  ('xiuzhen', '修真', 'NOVEL', 2),
  ('lishi', '历史', 'NOVEL', 3),
  ('yanqing', '言情', 'NOVEL', 4),
  ('wangyou', '网游', 'NOVEL', 5),
  ('kehuan', '科幻', 'NOVEL', 6),
  ('qita', '其他', 'NOVEL', 7);