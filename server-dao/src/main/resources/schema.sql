DROP DATABASE IF EXISTS blog_db;

CREATE DATABASE blog_db
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE blog_db;

-- ----------------------------
--  Table structure for tb_user
-- ----------------------------
DROP TABLE
IF EXISTS tb_user;

CREATE TABLE tb_user
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  username     VARCHAR(32)                           NOT NULL
  COMMENT '用户名',
  realname     VARCHAR(32)                           NOT NULL
  COMMENT '真实姓名',
  password     VARCHAR(64)                           NOT NULL
  COMMENT '密码',
  salt         VARCHAR(64)                           NOT NULL
  COMMENT '密码盐',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '用户表';
CREATE UNIQUE INDEX username_UNIQUE
  ON tb_user (username);

-- ----------------------------
--  Table structure for tb_role
-- ----------------------------
DROP TABLE
IF EXISTS tb_role;

CREATE TABLE tb_role
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '角色代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '角色名称',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '角色表';
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_role (code);

-- ----------------------------
--  Table structure for tb_menu
-- ----------------------------
DROP TABLE
IF EXISTS tb_menu;

CREATE TABLE tb_menu
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '菜单代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '菜单名称',
  pcode        VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '父菜单代码',
  url          VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单地址',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '菜单排序(从0开始)',
  icon         VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单图标的样式',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '菜单表';
CREATE INDEX sort_ix
  ON tb_menu (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_menu (code);

-- ----------------------------
--  Table structure for tb_user_role
-- ----------------------------
DROP TABLE
IF EXISTS tb_user_role;

CREATE TABLE tb_user_role
(
  username  VARCHAR(32) NOT NULL
  COMMENT '用户名',
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  PRIMARY KEY (username, role_code)
)
  COMMENT '用户角色表';

-- ----------------------------
--  Table structure for rtb_ole_menu
-- ----------------------------
DROP TABLE
IF EXISTS tb_role_menu;

CREATE TABLE tb_role_menu
(
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  menu_code VARCHAR(32) NOT NULL
  COMMENT '菜单代码',
  PRIMARY KEY (role_code, menu_code)
)
  COMMENT '角色菜单表';

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

-- ----------------------------
--  Table structure for tb_section
-- ----------------------------
DROP TABLE
IF EXISTS tb_section;

CREATE TABLE tb_section
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         INT(11)                               NOT NULL
  COMMENT '章节代码',
  title        VARCHAR(64)                           NOT NULL
  COMMENT '标题',
  content      LONGTEXT                              NOT NULL
  COMMENT '内容',
  novel_code   INT(11)                               NOT NULL
  COMMENT '小说代码',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '章节表';
CREATE UNIQUE INDEX code_UNIQUE
  ON tb_section (code);
CREATE INDEX ix_novel_code
  ON tb_section (novel_code);


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

-- ----------------------------
--  data for tb_user
-- ----------------------------
INSERT INTO tb_user
(username, realname, password, salt)
VALUES
  ('admin', '管理员', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaotiao', '小跳', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin', '小新', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin2', '小新2', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin3', '小新3', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin4', '小新4', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin5', '小新5', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin6', '小新6', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin7', '小新7', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin8', '小新8', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin9', '小新9', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin10', '小新10', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd'),
  ('xiaoxin11', '小新11', 'df9e06125caf2f421535bc725b9c96d329ae377b', '5cf6b1a302cabcbd');