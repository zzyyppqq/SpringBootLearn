CREATE TABLE `role_permission`
(
    `id`            bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`       bigint unsigned NOT NULL COMMENT '角色Id',
    `permission_id` bigint unsigned NOT NULL COMMENT '权限Id',
    `create_time`   bigint unsigned NOT NULL DEFAULT 0 COMMENT '创建时间戳，毫秒级',
    `update_time`   bigint unsigned NOT NULL DEFAULT 0 COMMENT '更新时间戳，毫秒级',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000000
  CHARACTER SET = utf8mb4 COMMENT = '角色权限关联表';