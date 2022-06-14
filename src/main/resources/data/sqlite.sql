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
-- 创建系统配置
insert into config(id, key, value)
values (1, 'db:version', '1.0.0'),
       (2, 'user:register:url', '0'),
       (3, 'user:register:oauth2', '0');
-- 创建初始管理员
insert into user(id, name, nickname, password, admin, add_time)
values (61201, 'admin', 'Administrator', 'c3284d0f94606de1fd2af172aba15bf3', 1, 1655116201000);