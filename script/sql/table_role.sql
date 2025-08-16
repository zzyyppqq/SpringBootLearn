CREATE TABLE `role`
(
    `id`          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(256)    NOT NULL COMMENT '名称',
    `description` varchar(256)    NOT NULL DEFAULT '' COMMENT '描述',
    `create_time` bigint unsigned NOT NULL DEFAULT 0 COMMENT '创建时间戳，毫秒级',
    `update_time` bigint unsigned NOT NULL DEFAULT 0 COMMENT '更新时间戳，毫秒级',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  CHARACTER SET = utf8mb4 COMMENT = '角色表';

alter table `role`
    add unique key (`name`);
