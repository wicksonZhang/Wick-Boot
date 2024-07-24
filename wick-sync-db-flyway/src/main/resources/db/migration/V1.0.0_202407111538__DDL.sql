DROP TABLE IF EXISTS `system_data_source_config`;
CREATE TABLE `system_data_source_config`  (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键编号',
     `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
     `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
     `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
     `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
     `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '数据源配置表';

-- ----------------------------
-- Records of infra_data_source_config
-- ----------------------------
INSERT INTO `wick_boot`.`system_data_source_config` (`id`, `name`, `url`, `username`, `password`, `deleted`, `create_time`, `update_time`, `create_by`, `update_by`)
VALUES (1, 'master', 'jdbc:mysql://139.9.202.135:1653/wick_boot?useUnicode=true&characterEncoding=UTF-8&useSSL=false', 'root', 'P@ssw0rd2024', b'0', '2024-07-23 10:28:30', '2024-07-23 10:28:30', '2', '2');

BEGIN;
COMMIT;
