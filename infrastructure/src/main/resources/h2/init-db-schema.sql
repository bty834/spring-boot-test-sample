CREATE TABLE `uber_flow_order`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`       varchar(15) NOT NULL COMMENT '订单编号，业务uk',
    `deleted`    tinyint     NOT NULL DEFAULT '0' COMMENT '标记删除 1删除 0未删除',
    `created_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `updated_at` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT ='流水表';

