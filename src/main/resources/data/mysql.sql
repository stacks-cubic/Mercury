-- 配置表
CREATE TABLE `config`
(
    `id`    int NOT NULL AUTO_INCREMENT,
    `key`   varchar(255) NOT NULL,
    `value` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);
-- 用户表
CREATE TABLE `user`
(
    `id`         int NOT NULL AUTO_INCREMENT,
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
    `code`     varchar(32) NOT NULL,
    `uid`      int NOT NULL,
    `external` tinyint(1) NOT NULL DEFAULT 0,
    `ip`       varchar(255) NOT NULL,
    `platform` varchar(255) NOT NULL,
    `device`   varchar(255) NOT NULL,
    `issued`   varchar(15) NOT NULL,
    `expire`   varchar(15) NOT NULL,
    PRIMARY KEY (`code`)
);
-- 创建系统配置
insert into `config`(`id`, `key`, `value`)
values (1, 'db:version', '1.0.0'),
       (2, 'user:register:url', '0'),
       (3, 'user:register:oauth2', '0');
-- 创建初始管理员
insert into user(`id`, `name`, `nickname`, `password`, `admin`, `add_time`)
values (61201, 'admin', 'Administrator', 'c3284d0f94606de1fd2af172aba15bf3', 1, 1655116201000);