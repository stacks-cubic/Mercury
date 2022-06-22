-- 配置表
CREATE TABLE `config`
(
    `id`    int          NOT NULL AUTO_INCREMENT,
    `key`   varchar(255) NOT NULL,
    `value` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
-- 用户表
CREATE TABLE `user`
(
    `id`         int         NOT NULL AUTO_INCREMENT,
    `avatar`     longtext,
    `name`       varchar(24) NOT NULL,
    `nickname`   varchar(24),
    `password`   varchar(32) NOT NULL,
    `admin`      tinyint(1) NOT NULL DEFAULT 0,
    `mfa`        varchar(64),
    `add_time`   varchar(15) NOT NULL,
    `last_login` varchar(15),
    PRIMARY KEY (`id`)
);
-- 令牌表
CREATE TABLE `token`
(
    `code`     varchar(32)  NOT NULL,
    `uid`      int          NOT NULL,
    `external` tinyint(1) NOT NULL DEFAULT 0,
    `ip`       varchar(255) NOT NULL,
    `platform` varchar(255) NOT NULL,
    `device`   varchar(255) NOT NULL,
    `issued`   varchar(15)  NOT NULL,
    `expire`   varchar(15)  NOT NULL,
    PRIMARY KEY (`code`)
);
-- 代理表
CREATE TABLE "proxy"
(
    "id"     int         NOT NULL AUTO_INCREMENT,
    "name"   varchar(24) NOT NULL,
    "host"   varchar(24) NOT NULL,
    "port"   int         NOT NULL,
    "source" varchar(32) NOT NULL,
    "mode"   int         NOT NULL,
    PRIMARY KEY ("id")
);
-- 书签表
CREATE TABLE "mark"
(
    "id"       int         NOT NULL AUTO_INCREMENT,
    "gid"      int,
    "uid"      int         NOT NULL,
    "ssid"     int         NOT NULL DEFAULT 0,
    "title"    varchar(24) NOT NULL,
    "icon"     varchar(32) NOT NULL,
    "color"    varchar(6),
    "describe" varchar(32),
    "weight"   int         NOT NULL DEFAULT 0,
    "superior" int,
    "service"  tinyint(1) NOT NULL DEFAULT 0,
    "era"      varchar(64),
    "ira"      varchar(64) NOT NULL,
    "hide"     int         NOT NULL DEFAULT 3,
    "date"     varchar(15) NOT NULL,
    PRIMARY KEY ("id")
);
-- 分组表
CREATE TABLE "group"
(
    "id"     int         NOT NULL AUTO_INCREMENT,
    "uid"    int         NOT NULL,
    "name"   varchar(24) NOT NULL,
    "weight" int         NOT NULL DEFAULT 0,
    "fold"   tinyint(1) NOT NULL DEFAULT 0,
    "hide"   int         NOT NULL DEFAULT 0,
    "date"   varchar(15) NOT NULL,
    PRIMARY KEY ("id")
);
-- 创建系统配置
insert into `config`(`id`, `key`, `value`)
values (1, 'db:version', '1.0.0'),
       (2, 'user:register:open', ${registerState}),
       (3, 'user:register:url', '0'),
       (4, 'user:register:oauth2', '0'),
       (5, 'network:proxy:mode', '0'),
       (6, 'network:zt:id', ''),
       (7, 'page:theme:dark', false),
       (8, 'page:theme:color', '#333333');
-- 创建初始管理员
insert into user(id, name, nickname, password, admin, add_time)
values (61201, ${adminName}, ${adminNickname}, ${adminPassword}, 1, ${addTime});