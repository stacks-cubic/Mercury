-- 配置表
CREATE TABLE "config"
(
    "id"    INTEGER(2) NOT NULL,
    "key"   TEXT NOT NULL,
    "value" TEXT NOT NULL,
    PRIMARY KEY ("id")
);
-- 用户表
CREATE TABLE "user"
(
    "id"         INTEGER(5) NOT NULL,
    "avatar"     TEXT,
    "name"       TEXT(24) NOT NULL,
    "nickname"   TEXT(24),
    "password"   TEXT(32) NOT NULL,
    "admin"      INTEGER(1) NOT NULL DEFAULT 0,
    "mfa"        TEXT(64),
    "add_time"   TEXT(15) NOT NULL,
    "last_login" TEXT(15),
    PRIMARY KEY ("id")
);
-- 令牌表
CREATE TABLE "token"
(
    "code"     TEXT(32) NOT NULL,
    "uid"      INTEGER(5) NOT NULL,
    "external" INTEGER(1) NOT NULL DEFAULT 0,
    "ip"       TEXT NOT NULL,
    "platform" TEXT NOT NULL,
    "device"   TEXT NOT NULL,
    "issued"   TEXT(15) NOT NULL,
    "expire"   TEXT(15) NOT NULL,
    PRIMARY KEY ("code")
);
-- 代理表
CREATE TABLE "proxy"
(
    "id"     INTEGER(5) NOT NULL,
    "name"   TEXT(24) NOT NULL,
    "host"   TEXT(24) NOT NULL,
    "port"   INTEGER(5) NOT NULL,
    "source" TEXT(32) NOT NULL,
    "mode"   INTEGER(1) NOT NULL,
    PRIMARY KEY ("id")
);
-- 书签表
CREATE TABLE "mark"
(
    "id"       INTEGER(5) NOT NULL,
    "gid"      INTEGER(5),
    "uid"      INTEGER(5) NOT NULL,
    "ssid"     INTEGER(5) NOT NULL DEFAULT 0,
    "title"    TEXT(24) NOT NULL,
    "icon"     TEXT(32) NOT NULL,
    "color"    TEXT(6),
    "describe" TEXT(32),
    "weight"   INTEGER(3) NOT NULL DEFAULT 0,
    "superior" INTEGER(5),
    "service"  INTEGER(1) NOT NULL DEFAULT 0,
    "era"      TEXT(64),
    "ira"      TEXT(64) NOT NULL,
    "hide"     INTEGER(1) NOT NULL DEFAULT 3,
    "date"     TEXT(15) NOT NULL,
    PRIMARY KEY ("id")
);
-- 分组表
CREATE TABLE "group"
(
    "id"     INTEGER(5) NOT NULL,
    "uid"    INTEGER(5) NOT NULL,
    "name"   TEXT(24) NOT NULL,
    "weight" INTEGER(3) NOT NULL DEFAULT 0,
    "fold"   INTEGER(1) NOT NULL DEFAULT 0,
    "hide"   INTEGER(1) NOT NULL DEFAULT 0,
    "date"   TEXT(15) NOT NULL,
    PRIMARY KEY ("id")
);
-- 创建系统配置
insert into config(id, key, value)
values (1, 'db:version', '1.0.0'),
       (2, 'user:register:open', ${registerState}),
       (3, 'user:register:url', '0'),
       (4, 'user:register:oauth2', '0'),
       (5, 'network:proxy:mode', '0'),
       (6, 'network:zt:id', '');
-- 创建初始管理员
insert into user(id, name, nickname, password, admin, add_time)
values (61201, ${adminName}, ${adminNickname}, ${adminPassword}, 1, ${addTime});