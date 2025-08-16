CREATE TABLE `permission`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type`        tinyint         NOT NULL COMMENT '权限类型',
    `name`        varchar(128)    NOT NULL COMMENT '权限名称',
    `description` varchar(256)    NOT NULL DEFAULT '' COMMENT '权限描述',
    `create_time` bigint unsigned NOT NULL DEFAULT 0 COMMENT '创建时间戳，毫秒级',
    `update_time` bigint unsigned NOT NULL DEFAULT 0 COMMENT '更新时间戳，毫秒级',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  CHARACTER SET = utf8mb4 COMMENT = '权限表';


alter table `permission`
    add unique key (`name`);