CREATE TABLE `user`
(
`uid`         bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '用户Id，也是主键',
`username`    varchar(45)      NOT NULL COMMENT '用户名',
`password`    varchar(1000)    NOT NULL COMMENT '登录密码',
`nick_name`   varchar(45)      NOT NULL DEFAULT '' COMMENT '昵称',
`disabled`    tinyint unsigned NOT NULL DEFAULT 0 COMMENT '用户是否被禁用：0否1是',
`create_time` bigint unsigned  NOT NULL DEFAULT 0 COMMENT '创建时间戳，毫秒级',
`update_time` bigint unsigned  NOT NULL DEFAULT 0 COMMENT '更新时间戳，毫秒级',
PRIMARY KEY (`uid`)
) ENGINE = InnoDB
AUTO_INCREMENT = 1000000
CHARACTER SET = utf8mb4 COMMENT = '用户表';

alter table `user` add unique key (`username`);