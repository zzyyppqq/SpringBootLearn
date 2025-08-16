CREATE TABLE `menu`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`          varchar(256)    NOT NULL COMMENT '名称',
    `show_name`     varchar(128)    NOT NULL COMMENT '显示名称',
    `description`   varchar(256)    NOT NULL DEFAULT '' COMMENT '描述',
    `path`          varchar(128)    NOT NULL COMMENT '菜单路径',
    `order`         int             NOT NULL DEFAULT 0 COMMENT '排序',
    `parent_id`     bigint unsigned NOT NULL DEFAULT 0 COMMENT '父菜单Id',
    `permission_id` bigint unsigned NOT NULL COMMENT '权限Id',
    `create_time`   bigint unsigned NOT NULL DEFAULT 0 COMMENT '创建时间戳，毫秒级',
    `update_time`   bigint unsigned NOT NULL DEFAULT 0 COMMENT '更新时间戳，毫秒级',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  CHARACTER SET = utf8mb4 COMMENT = '菜单表';

alter table `menu`
    add unique key (`name`);
