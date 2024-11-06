-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(4) NULL DEFAULT 1 COMMENT '性别((1:男;2:女))',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '头像地址',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '手机号码',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态((1:正常;0:禁用))',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_name`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'root', '超级管理员', 1, '$2a$10$xVWsNOhHrCxh5UbpCE7/HuJ.PAOKcYAqRxD2CO2nVnJS.IAXkr5aq', NULL, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, NULL, NULL, 'wickson@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);
INSERT INTO `system_user` VALUES (2, 'admin', '系统管理员', 1, '$2a$10$8/8PxGHA.30EeWg8x4/4BuWuCUJubFbGJXyUYRs7RaJEdVvEMRbWe', 2, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, NULL, NULL, 'wickson@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);
INSERT INTO `system_user` VALUES (3, 'test', '测试小用户', 1, '$2a$10$MPJkNw.hKT/fZOgwYP8q9eu/rFJJDsNov697AmdkHNJkpjIpVSw2q', 3, 'https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif', '17621210123', 1, NULL, NULL, 'wickson@163.com', 0, '2023-04-12 01:31:29', '2023-04-12 01:31:29', 1, 1);


-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
  `data_scope` tinyint NULL DEFAULT NULL COMMENT '数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'ROOT', 1, 1, 0, 0, '2021-05-21 14:56:51', '2018-12-23 16:00:00', 1, 1);
INSERT INTO `system_role` VALUES (2, '系统管理员', 'ADMIN', 1, 1, 1, 0, '2021-03-25 12:39:54', '2022-11-05 00:22:02', 1, 1);
INSERT INTO `system_role` VALUES (3, '访问游客', 'GUEST', 3, 1, 2, 0, '2021-05-26 15:49:05', '2019-05-05 16:00:00', 1, 1);


-- ----------------------------
-- Table structure for system_dept
-- ----------------------------
DROP TABLE IF EXISTS `system_dept`;
CREATE TABLE `system_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '部门名称',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编号',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父节点id',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '父节点id路径',
  `sort` int NULL DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态(1:正常;0:禁用)',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_dept
-- ----------------------------
INSERT INTO `system_dept` VALUES (1, '威客技术', 'Wick', 0, '0', 1, 1, 0, '2023-04-19 12:46:37', '2023-08-19 12:46:37', 1, 1);
INSERT INTO `system_dept` VALUES (2, '研发部门', 'RD001', 1, '0,1', 1, 1, 0, '2023-04-19 12:46:37', '2023-08-19 12:46:37', 1, 1);
INSERT INTO `system_dept` VALUES (3, '测试部门', 'QA001', 1, '0,1', 1, 1, 0, '2023-04-19 12:46:37', '2023-08-19 12:46:37', 1, 1);


-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` int NULL DEFAULT NULL COMMENT '父菜单ID',
  `type` tinyint NULL DEFAULT NULL COMMENT '菜单类型(1：菜单；2：目录；3：外链；4：按钮)',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '菜单名称',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由名称（Vue Router 中用于命名路由）',
  `route_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '路由路径（Vue Router 中定义的 URL 路径）',
  `component` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `perm` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '菜单图标',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '状态(0:禁用;1:开启)',
  `redirect` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '跳转路径',
  `tree_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `always_show` tinyint NULL DEFAULT NULL COMMENT '【目录】只有一个子路由是否始终显示(1:是 0:否)',
  `keep_alive` tinyint NULL DEFAULT NULL COMMENT '【菜单】是否开启页面缓存(1:是 0:否)',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;
-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES (1, 0, 2, '系统管理', '', '/system', 'Layout', NULL, 'system', 1, 1, '/system/user', '0', NULL, NULL, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (2, 1, 1, '用户管理', 'User', 'user', 'system/user/index', NULL, 'user', 1, 1, NULL, '0,1', NULL, 1, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (3, 1, 1, '角色管理', 'Role', 'role', 'system/role/index', NULL, 'role', 2, 1, NULL, '0,1', NULL, 1, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (4, 1, 1, '菜单管理', 'Menu', 'menu', 'system/menu/index', NULL, 'menu', 3, 1, NULL, '0,1', NULL, 1, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (5, 1, 1, '部门管理', 'Dept', 'dept', 'system/dept/index', NULL, 'tree', 4, 1, NULL, '0,1', NULL, 1, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (6, 1, 1, '字典管理', 'Dict', 'dict', 'system/dict/index', NULL, 'dict', 5, 1, NULL, '0,1', NULL, 1, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (7, 1, 1, '日志管理', 'Logger', '/logger', 'system/logger/index', NULL, 'menu', 6, 1, '/system/logger/login-log', '0,1', NULL, 1, b'0', '2024-06-25 10:14:37', '2024-10-14 17:20:41', '2', '2');
INSERT INTO `system_menu` VALUES (8, 7, 1, '登录日志', 'Login', 'login-log', 'system/logger/login-log/index', NULL, 'user', 1, 1, '', '0,1,7', 0, 0, b'0', '2024-06-25 11:19:43', '2024-06-25 16:32:47', '2', '2');
INSERT INTO `system_menu` VALUES (9, 7, 1, '操作日志', 'Operate', 'operate-log', 'system/logger/operate-log/index', NULL, 'document', 1, 1, '', '0,1,7', NULL, NULL, b'0', '2024-06-25 16:34:34', '2024-06-25 16:35:14', '2', '2');
INSERT INTO `system_menu` VALUES (10, 2, 4, '新增用户', NULL, '', NULL, 'system:user:add', '', 1, 1, '', '0,1,2', NULL, NULL, b'0', '2021-08-28 09:12:21', '2021-08-28 09:12:21', '1', '1');
INSERT INTO `system_menu` VALUES (11, 2, 4, '修改用户', NULL, '', NULL, 'system:user:update', '', 2, 1, '', '0,1,2', NULL, NULL, b'0', '2022-11-05 01:26:44', '2022-11-05 01:26:44', '1', '1');
INSERT INTO `system_menu` VALUES (12, 2, 4, '删除用户', NULL, '', NULL, 'system:user:delete', '', 3, 1, '', '0,1,2', NULL, NULL, b'0', '2022-11-05 01:27:13', '2022-11-05 01:27:13', '1', '1');
INSERT INTO `system_menu` VALUES (13, 2, 4, '获取用户信息', NULL, '', NULL, 'system:user:query', '', 4, 1, '', '0,1,2', NULL, NULL, b'0', '2022-11-05 01:27:13', '2022-11-05 01:27:13', '1', '1');
INSERT INTO `system_menu` VALUES (14, 2, 4, '获取用户分页', NULL, '', NULL, 'system:user:query', '', 5, 1, '', '0,1,2', NULL, NULL, b'0', '2022-11-05 01:27:13', '2022-11-05 01:27:13', '1', '1');
INSERT INTO `system_menu` VALUES (15, 2, 4, '导出用户信息', NULL, '', NULL, 'system:user:export', '', 6, 1, '', '0,1,2', NULL, NULL, b'0', '2022-11-05 01:27:13', '2022-11-05 01:27:13', '1', '1');
INSERT INTO `system_menu` VALUES (16, 2, 4, '导入用户信息', NULL, '', NULL, 'system:user:import', '', 7, 1, '', '0,1,2', NULL, NULL, b'0', '2022-11-05 01:27:13', '2022-11-05 01:27:13', '1', '1');
INSERT INTO `system_menu` VALUES (17, 2, 4, '重置密码', NULL, '', NULL, 'system:user:reset_pwd', '', 4, 1, NULL, '0,1,2', NULL, NULL, b'0', '2023-05-21 00:49:18', '2023-05-21 00:49:18', '1', '1');
INSERT INTO `system_menu` VALUES (18, 3, 4, '角色新增', NULL, '', NULL, 'system:role:add', '', 1, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:39:09', '2023-05-20 23:39:09', '1', '1');
INSERT INTO `system_menu` VALUES (19, 3, 4, '角色编辑', NULL, '', NULL, 'system:role:update', '', 2, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:40:31', '2023-05-20 23:40:31', '1', '1');
INSERT INTO `system_menu` VALUES (20, 3, 4, '角色删除', NULL, '', NULL, 'system:role:delete', '', 3, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:41:08', '2023-05-20 23:41:08', '1', '1');
INSERT INTO `system_menu` VALUES (21, 3, 4, '分配权限', NULL, '', NULL, 'system:role:assign', '', 3, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:41:08', '2023-05-20 23:41:08', '1', '1');
INSERT INTO `system_menu` VALUES (22, 3, 4, '获取角色信息', NULL, '', NULL, 'system:role:query', '', 4, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:41:08', '2023-05-20 23:41:08', '1', '1');
INSERT INTO `system_menu` VALUES (23, 3, 4, '获取角色分页', NULL, '', NULL, 'system:role:query', '', 5, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:41:08', '2023-05-20 23:41:08', '1', '1');
INSERT INTO `system_menu` VALUES (24, 3, 4, '获取角色下拉选项', NULL, '', NULL, 'system:role:options', '', 6, 1, NULL, '0,1,3', NULL, NULL, b'0', '2023-05-20 23:41:08', '2023-05-20 23:41:08', '1', '1');
INSERT INTO `system_menu` VALUES (25, 4, 4, '菜单新增', NULL, '', NULL, 'system:menu:add', '', 1, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:41:35', '2023-05-20 23:41:35', '1', '1');
INSERT INTO `system_menu` VALUES (26, 4, 4, '菜单编辑', NULL, '', NULL, 'system:menu:update', '', 3, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:41:58', '2023-05-20 23:41:58', '1', '1');
INSERT INTO `system_menu` VALUES (27, 4, 4, '菜单删除', NULL, '', NULL, 'system:menu:delete', '', 3, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:44:18', '2023-05-20 23:44:18', '1', '1');
INSERT INTO `system_menu` VALUES (28, 4, 4, '获取菜单列表', NULL, '', NULL, 'system:menu:query', '', 4, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:44:18', '2023-05-20 23:44:18', '1', '1');
INSERT INTO `system_menu` VALUES (29, 4, 4, '获取菜单数据', NULL, '', NULL, 'system:menu:query', '', 5, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:44:18', '2023-05-20 23:44:18', '1', '1');
INSERT INTO `system_menu` VALUES (30, 4, 4, '获取菜单选项', NULL, '', NULL, 'system:menu:options', '', 6, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:44:18', '2023-05-20 23:44:18', '1', '1');
INSERT INTO `system_menu` VALUES (31, 4, 4, '获取菜单路由', NULL, '', NULL, 'system:menu:routes', '', 7, 1, NULL, '0,1,4', NULL, NULL, b'0', '2023-05-20 23:44:18', '2023-05-20 23:44:18', '1', '1');
INSERT INTO `system_menu` VALUES (32, 5, 4, '部门新增', NULL, '', NULL, 'system:dept:add', '', 1, 1, NULL, '0,1,5', NULL, NULL, b'0', '2023-05-20 23:45:00', '2023-05-20 23:45:00', '1', '1');
INSERT INTO `system_menu` VALUES (33, 5, 4, '部门编辑', NULL, '', NULL, 'system:dept:update', '', 2, 1, NULL, '0,1,5', NULL, NULL, b'0', '2023-05-20 23:46:16', '2023-05-20 23:46:16', '1', '1');
INSERT INTO `system_menu` VALUES (34, 5, 4, '部门删除', NULL, '', NULL, 'system:dept:delete', '', 3, 1, NULL, '0,1,5', NULL, NULL, b'0', '2023-05-20 23:46:36', '2023-05-20 23:46:36', '1', '1');
INSERT INTO `system_menu` VALUES (35, 5, 4, '获取部门数据', NULL, '', NULL, 'system:dept:query', '', 4, 1, NULL, '0,1,5', NULL, NULL, b'0', '2023-05-20 23:46:36', '2023-05-20 23:46:36', '1', '1');
INSERT INTO `system_menu` VALUES (36, 5, 4, '获取部门列表', NULL, '', NULL, 'system:dept:query', '', 5, 1, NULL, '0,1,5', NULL, NULL, b'0', '2023-05-20 23:46:36', '2023-05-20 23:46:36', '1', '1');
INSERT INTO `system_menu` VALUES (37, 5, 4, '获取部门下拉选项', NULL, '', NULL, 'system:dept:options', '', 6, 1, NULL, '0,1,5', NULL, NULL, b'0', '2023-05-20 23:46:36', '2023-05-20 23:46:36', '1', '1');
INSERT INTO `system_menu` VALUES (38, 6, 4, '字典类型新增', NULL, '', NULL, 'system:dict-type:add', '', 1, 1, NULL, '0,1,6', NULL, NULL, b'0', '2023-05-21 00:16:06', '2023-05-21 00:16:06', '1', '1');
INSERT INTO `system_menu` VALUES (39, 6, 4, '字典类型编辑', NULL, '', NULL, 'system:dict-type:update', '', 2, 1, NULL, '0,1,6', NULL, NULL, b'0', '2023-05-21 00:27:37', '2023-05-21 00:27:37', '1', '1');
INSERT INTO `system_menu` VALUES (40, 6, 4, '字典类型删除', NULL, '', NULL, 'system:dict-type:delete', '', 3, 1, NULL, '0,1,6', NULL, NULL, b'0', '2023-05-21 00:29:39', '2023-05-21 00:29:39', '1', '1');
INSERT INTO `system_menu` VALUES (41, 6, 4, '获取字典类型数据', NULL, '', NULL, 'system:dict-type:query', '', 4, 1, NULL, '0,1,6', NULL, NULL, b'0', '2023-05-21 00:29:39', '2023-05-21 00:29:39', '1', '1');
INSERT INTO `system_menu` VALUES (42, 6, 4, '获取字典类型分页', NULL, '', NULL, 'system:dict-type:query', '', 5, 1, NULL, '0,1,6', NULL, NULL, b'0', '2023-05-21 00:29:39', '2023-05-21 00:29:39', '1', '1');
INSERT INTO `system_menu` VALUES (43, 6, 4, '获取字典类型选项', NULL, '', NULL, 'system:dict-type:options', '', 6, 1, NULL, '0,1,6', NULL, NULL, b'0', '2023-05-21 00:29:39', '2023-05-21 00:29:39', '1', '1');
INSERT INTO `system_menu` VALUES (44, 64, 4, '字典数据新增', NULL, '', NULL, 'system:dict-data:add', '', 1, 1, NULL, '0,1,70', NULL, NULL, b'0', '2023-05-21 00:46:56', '2024-10-14 17:14:57', '1', '2');
INSERT INTO `system_menu` VALUES (45, 64, 4, '字典数据编辑', NULL, '', NULL, 'system:dict-data:update', '', 2, 1, NULL, '0,1,70', NULL, NULL, b'0', '2023-05-21 00:47:36', '2024-10-14 17:16:09', '1', '2');
INSERT INTO `system_menu` VALUES (46, 64, 4, '字典数据删除', NULL, '', NULL, 'system:dict-data:delete', '', 3, 1, NULL, '0,1,70', NULL, NULL, b'0', '2023-05-21 00:48:10', '2024-10-14 17:16:44', '1', '2');
INSERT INTO `system_menu` VALUES (47, 64, 4, '获取字典数据', NULL, '', NULL, 'system:dict-data:query', '', 4, 1, NULL, '0,1,70', NULL, NULL, b'0', '2023-05-21 00:48:10', '2024-10-14 17:16:59', '1', '2');
INSERT INTO `system_menu` VALUES (48, 64, 4, '获取字典数据分页', NULL, '', NULL, 'system:dict-data:query', '', 5, 1, NULL, '0,1,70', NULL, NULL, b'0', '2023-05-21 00:48:10', '2024-10-14 17:17:32', '1', '2');
INSERT INTO `system_menu` VALUES (49, 64, 4, '获取字典数据选项', NULL, '', NULL, 'system:dict-data:options', '', 6, 1, NULL, '0,1,70', NULL, NULL, b'0', '2023-05-21 00:48:10', '2024-10-14 17:17:46', '1', '2');
INSERT INTO `system_menu` VALUES (50, 8, 4, '获取登录日志分页', NULL, '', NULL, 'system:login-log:query', '', 1, 1, NULL, '0,1,8', NULL, NULL, b'0', '2023-05-21 00:48:10', '2023-05-21 00:48:20', '1', '1');
INSERT INTO `system_menu` VALUES (51, 8, 4, '导出登录日志分页', NULL, '', NULL, 'system:login-log:export', '', 2, 1, NULL, '0,1,8', NULL, NULL, b'0', '2023-05-21 00:48:10', '2023-05-21 00:48:20', '1', '1');
INSERT INTO `system_menu` VALUES (52, 9, 4, '获取操作日志分页', NULL, '', NULL, 'system:operate-log:query', '', 1, 1, NULL, '0,1,9', NULL, NULL, b'0', '2023-05-21 00:48:10', '2023-05-21 00:48:20', '1', '1');
INSERT INTO `system_menu` VALUES (53, 9, 4, '导出操作日志分页', NULL, '', NULL, 'system:operate-log:export', '', 2, 1, NULL, '0,1,9', NULL, NULL, b'0', '2023-05-21 00:48:10', '2023-05-21 00:48:20', '1', '1');
INSERT INTO `system_menu` VALUES (54, 0, 2, '系统工具', '', '/tool', 'Layout', NULL, 'menu', 2, 1, '', '0', 1, 1, b'0', '2024-09-19 15:38:40', '2024-09-19 15:38:40', '1', '1');
INSERT INTO `system_menu` VALUES (55, 54, 1, '代码生成', 'Codegen', 'code-gen', 'tool/code-gen/index', NULL, 'code', 1, 1, '', '0,54', 0, 1, b'0', '2024-09-19 15:39:46', '2024-09-19 15:39:46', '1', '1');
INSERT INTO `system_menu` VALUES (56, 55, 4, '获取数据源数据表', NULL, '', NULL, 'tool:code-gen:query', '', 1, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:30:55', '2024-09-19 16:30:55', '1', '1');
INSERT INTO `system_menu` VALUES (57, 55, 4, '导入数据表', NULL, '', NULL, 'tool:code-gen:import', '', 2, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:30:55', '2024-09-19 16:30:55', '1', '1');
INSERT INTO `system_menu` VALUES (58, 55, 4, '获取代码生成分页', NULL, '', NULL, 'tool:code-gen:query', '', 2, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:30:55', '2024-09-19 16:30:55', '1', '1');
INSERT INTO `system_menu` VALUES (59, 55, 4, '修改代码生成信息', NULL, '', NULL, 'tool:code-gen:update', '', 3, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:31:19', '2024-09-19 16:31:19', '1', '1');
INSERT INTO `system_menu` VALUES (60, 55, 4, '删除代码生成信息', NULL, '', NULL, 'tool:code-gen:delete', '', 4, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:31:19', '2024-09-19 16:31:19', '1', '1');
INSERT INTO `system_menu` VALUES (61, 55, 4, '预览代码', NULL, '', NULL, 'tool:code-gen:preview', '', 4, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:31:19', '2024-09-19 16:31:19', '1', '1');
INSERT INTO `system_menu` VALUES (62, 55, 4, '同步代码生成信息', NULL, '', NULL, 'tool:code-gen:async', '', 5, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:31:19', '2024-09-19 16:31:19', '1', '1');
INSERT INTO `system_menu` VALUES (63, 55, 4, '生成代码信息', NULL, '', NULL, 'tool:code-gen:download', '', 6, 1, '', '0,54,55', NULL, NULL, b'0', '2024-09-19 16:31:19', '2024-09-19 16:31:19', '1', '1');
INSERT INTO `system_menu` VALUES (64, 1, 1, '字典数据', 'DictData', 'dict-data', 'system/dict/item', NULL, 'menu', 5, 0, '', '0,1', 0, 0, b'0', '2024-10-14 17:09:31', '2024-10-16 09:13:45', '2', '2');
INSERT INTO `system_menu` VALUES (65, 54, 1, '数据源配置', 'DataSource', 'data-source', 'tool/data-source/index', NULL, '', 1, 1, NULL, '0,54', NULL, 1, b'0', '2024-10-15 09:41:39', '2024-10-15 09:41:39', '1', '1');
INSERT INTO `system_menu` VALUES (66, 65, 4, '数据源配置新增', NULL, '', NULL, 'tool:data-source:add', NULL, 1, 1, NULL, '0,54,65', NULL, NULL, b'0', '2024-10-15 09:41:39', '2024-10-15 09:41:39', '1', '1');
INSERT INTO `system_menu` VALUES (67, 65, 4, '数据源配置编辑', NULL, '', NULL, 'tool:data-source:update', NULL, 2, 1, NULL, '0,54,65', NULL, NULL, b'0', '2024-10-15 09:41:39', '2024-10-15 09:41:39', '1', '1');
INSERT INTO `system_menu` VALUES (68, 65, 4, '数据源配置删除', NULL, '', NULL, 'tool:data-source:delete', NULL, 3, 1, NULL, '0,54,65', NULL, NULL, b'0', '2024-10-15 09:41:39', '2024-10-15 09:41:39', '1', '1');
INSERT INTO `system_menu` VALUES (69, 65, 4, '获取数据源配置数据', NULL, '', NULL, 'tool:data-source:query', NULL, 4, 1, NULL, '0,54,65', NULL, NULL, b'0', '2024-10-15 09:41:39', '2024-10-15 09:41:39', '1', '1');
INSERT INTO `system_menu` VALUES (70, 65, 4, '获取数据源配置分页', NULL, '', NULL, 'tool:data-source:query', NULL, 4, 1, NULL, '0,54,65', NULL, NULL, b'0', '2024-10-15 09:41:40', '2024-10-15 09:41:40', '1', '1');
INSERT INTO `system_menu` VALUES (71, 0, 2, '系统监控', NULL, '/monitor', 'Layout', NULL, 'eye-open', 3, 1, '/monitor/online', '0', 1, 1, b'0', '2024-10-25 11:07:55', '2024-10-25 14:58:08', '2', '2');
INSERT INTO `system_menu` VALUES (72, 71, 1, '在线用户', 'Online', 'online', 'monitor/online/index', NULL, 'user', 1, 1, '', '0,71', NULL, 1, b'0', '2024-10-25 14:52:16', '2024-10-25 14:53:22', '2', '2');
INSERT INTO `system_menu` VALUES (73, 72, 4, '获取在线用户分页', NULL, '', NULL, 'monitor:online:query', '', 1, 1, '', '0,71,72', 0, 1, b'0', '2024-10-25 17:44:59', '2024-10-25 17:44:59', '2', '2');
INSERT INTO `system_menu` VALUES (74, 72, 4, '强制退出在线用户', NULL, '', NULL, 'monitor:online:force-quit', '', 2, 1, '', '0,71,72', 0, 1, b'0', '2024-10-26 22:28:28', '2024-10-26 22:28:28', '2', '2');
INSERT INTO `system_menu` VALUES (75, 72, 4, '导出在线用户', NULL, '', NULL, 'monitor:online:export', '', 3, 1, '', '0,71,72', 0, 1, b'0', '2024-10-27 17:57:38', '2024-10-27 17:57:38', '2', '2');
INSERT INTO `system_menu` VALUES (76, 6, 4, '刷新字典缓存', NULL, '', NULL, 'system:dict-type:refresh', '', 7, 1, '', '0,1,6', 0, 1, b'0', '2024-11-05 11:52:53', '2024-11-05 11:54:14', '2', '2');


-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1);
INSERT INTO `system_user_role` VALUES (2, 2);
INSERT INTO `system_user_role` VALUES (3, 3);


-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES (2, 1);
INSERT INTO `system_role_menu` VALUES (2, 2);
INSERT INTO `system_role_menu` VALUES (2, 3);
INSERT INTO `system_role_menu` VALUES (2, 4);
INSERT INTO `system_role_menu` VALUES (2, 5);
INSERT INTO `system_role_menu` VALUES (2, 6);
INSERT INTO `system_role_menu` VALUES (2, 7);
INSERT INTO `system_role_menu` VALUES (2, 8);
INSERT INTO `system_role_menu` VALUES (2, 9);
INSERT INTO `system_role_menu` VALUES (2, 10);
INSERT INTO `system_role_menu` VALUES (2, 11);
INSERT INTO `system_role_menu` VALUES (2, 12);
INSERT INTO `system_role_menu` VALUES (2, 13);
INSERT INTO `system_role_menu` VALUES (2, 14);
INSERT INTO `system_role_menu` VALUES (2, 15);
INSERT INTO `system_role_menu` VALUES (2, 16);
INSERT INTO `system_role_menu` VALUES (2, 17);
INSERT INTO `system_role_menu` VALUES (2, 18);
INSERT INTO `system_role_menu` VALUES (2, 19);
INSERT INTO `system_role_menu` VALUES (2, 20);
INSERT INTO `system_role_menu` VALUES (2, 21);
INSERT INTO `system_role_menu` VALUES (2, 22);
INSERT INTO `system_role_menu` VALUES (2, 23);
INSERT INTO `system_role_menu` VALUES (2, 24);
INSERT INTO `system_role_menu` VALUES (2, 25);
INSERT INTO `system_role_menu` VALUES (2, 26);
INSERT INTO `system_role_menu` VALUES (2, 27);
INSERT INTO `system_role_menu` VALUES (2, 28);
INSERT INTO `system_role_menu` VALUES (2, 29);
INSERT INTO `system_role_menu` VALUES (2, 30);
INSERT INTO `system_role_menu` VALUES (2, 31);
INSERT INTO `system_role_menu` VALUES (2, 32);
INSERT INTO `system_role_menu` VALUES (2, 33);
INSERT INTO `system_role_menu` VALUES (2, 34);
INSERT INTO `system_role_menu` VALUES (2, 35);
INSERT INTO `system_role_menu` VALUES (2, 36);
INSERT INTO `system_role_menu` VALUES (2, 37);
INSERT INTO `system_role_menu` VALUES (2, 38);
INSERT INTO `system_role_menu` VALUES (2, 39);
INSERT INTO `system_role_menu` VALUES (2, 40);
INSERT INTO `system_role_menu` VALUES (2, 41);
INSERT INTO `system_role_menu` VALUES (2, 42);
INSERT INTO `system_role_menu` VALUES (2, 43);
INSERT INTO `system_role_menu` VALUES (2, 44);
INSERT INTO `system_role_menu` VALUES (2, 45);
INSERT INTO `system_role_menu` VALUES (2, 46);
INSERT INTO `system_role_menu` VALUES (2, 47);
INSERT INTO `system_role_menu` VALUES (2, 48);
INSERT INTO `system_role_menu` VALUES (2, 49);
INSERT INTO `system_role_menu` VALUES (2, 50);
INSERT INTO `system_role_menu` VALUES (2, 51);
INSERT INTO `system_role_menu` VALUES (2, 52);
INSERT INTO `system_role_menu` VALUES (2, 53);
INSERT INTO `system_role_menu` VALUES (2, 54);
INSERT INTO `system_role_menu` VALUES (2, 55);
INSERT INTO `system_role_menu` VALUES (2, 56);
INSERT INTO `system_role_menu` VALUES (2, 57);
INSERT INTO `system_role_menu` VALUES (2, 58);
INSERT INTO `system_role_menu` VALUES (2, 59);
INSERT INTO `system_role_menu` VALUES (2, 60);
INSERT INTO `system_role_menu` VALUES (2, 61);
INSERT INTO `system_role_menu` VALUES (2, 62);
INSERT INTO `system_role_menu` VALUES (2, 63);
INSERT INTO `system_role_menu` VALUES (2, 64);
INSERT INTO `system_role_menu` VALUES (2, 65);
INSERT INTO `system_role_menu` VALUES (2, 66);
INSERT INTO `system_role_menu` VALUES (2, 67);
INSERT INTO `system_role_menu` VALUES (2, 68);
INSERT INTO `system_role_menu` VALUES (2, 69);
INSERT INTO `system_role_menu` VALUES (2, 70);
INSERT INTO `system_role_menu` VALUES (2, 71);
INSERT INTO `system_role_menu` VALUES (2, 72);
INSERT INTO `system_role_menu` VALUES (2, 73);
INSERT INTO `system_role_menu` VALUES (2, 74);
INSERT INTO `system_role_menu` VALUES (2, 75);
INSERT INTO `system_role_menu` VALUES (2, 76);

-- ----------------------------
-- Table structure for system_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '类型名称',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '类型编码',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态(1:正常;0:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dict_code`(`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_dict_type
-- ----------------------------
INSERT INTO `system_dict_type` VALUES (1, '性别', 'gender', 1, '性别（1-男、2-女、0-未知）', b'0', '2019-12-06 19:03:32', '2024-11-05 14:38:26', '1', '2');
INSERT INTO `system_dict_type` VALUES (2, '使用状态', 'status', 1, '使用状态（1-启用、0-禁用）', b'0', '2024-11-05 10:23:52', '2024-11-05 15:31:02', '2', '2');
INSERT INTO `system_dict_type` VALUES (3, '菜单类型', 'type', 1, '菜单类型（1-菜单、2-目录、3-外链、4-按钮）', b'0', '2024-11-05 14:38:02', '2024-11-05 15:30:40', '2', '2');
INSERT INTO `system_dict_type` VALUES (4, '显示状态', 'visible', 1, '显示状态（1-显示、0-隐藏）', b'0', '2024-11-05 17:59:09', '2024-11-05 17:59:09', '2', '2');
INSERT INTO `system_dict_type` VALUES (5, '操作类型', 'operateType', 1, '操作类型（1-查询、2-新增、3-修改、4-删除、5-导出、6-导入、7-其他）', b'0', '2024-11-06 11:41:49', '2024-11-06 11:41:49', '2', '2');
INSERT INTO `system_dict_type` VALUES (6, '操作结果', 'resultCode', 1, '操作结果（0-成功、500-失败）', b'0', '2024-11-06 13:52:46', '2024-11-06 13:52:46', '2', '2');
INSERT INTO `system_dict_type` VALUES (7, '登录结果', 'result', 1, '登录结果（0-成功、10-账号或密码不正确、20-用户被禁用、30-验证码不存在、31-验证码不正确、100-未知异常）', b'0', '2024-11-06 14:15:04', '2024-11-06 14:15:04', '2', '2');
INSERT INTO `system_dict_type` VALUES (8, '登录类型', 'loginType', 1, '登录类型（100-账号登录、101-社交登录、103-短信登录、200-主动登出、202-强制登出）', b'0', '2024-11-06 14:36:19', '2024-11-06 14:36:19', '2', '2');

-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
  `tag_type` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签类型，用于前端样式展示（如success、warning等）',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（1正常 0停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表';

-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
INSERT INTO `system_dict_data` VALUES (1, 'gender', '男', '1', 1, 'primary', 1, '性别男', b'0', '2019-05-05 13:07:52', '2022-06-12 23:20:39', '1', '1');
INSERT INTO `system_dict_data` VALUES (2, 'gender', '女', '2', 2, 'success', 1, '性别女', b'0', '2019-04-19 11:33:00', '2019-07-02 14:23:05', '1', '1');
INSERT INTO `system_dict_data` VALUES (3, 'gender', '未知', '0', 3, 'success', 1, '未知性别', b'0', '2020-10-17 08:09:31', '2020-10-17 08:09:31', '1', '1');
INSERT INTO `system_dict_data` VALUES (4, 'status', '启用', '1', 1, 'success', 1, '启用', b'0', '2024-11-05 10:46:31', '2024-11-05 15:31:02', '2', '2');
INSERT INTO `system_dict_data` VALUES (5, 'status', '禁用', '0', 1, 'info', 1, '禁用', b'0', '2024-11-05 10:53:22', '2024-11-05 15:31:02', '2', '2');
INSERT INTO `system_dict_data` VALUES (7, 'type', '菜单', 'MENU', 1, 'success', 1, '菜单类型', b'0', '2024-11-05 16:31:26', '2024-11-05 17:51:16', '2', '2');
INSERT INTO `system_dict_data` VALUES (8, 'type', '目录', 'CATALOG', 2, 'warning', 1, '目录类型', b'0', '2024-11-05 16:35:57', '2024-11-05 17:51:42', '2', '2');
INSERT INTO `system_dict_data` VALUES (9, 'type', '外链', 'EXTLINK', 3, 'info', 1, '外链类型', b'0', '2024-11-05 16:38:40', '2024-11-05 17:51:51', '2', '2');
INSERT INTO `system_dict_data` VALUES (10, 'type', '按钮', 'BUTTON', 4, 'primary', 1, '按钮类型', b'0', '2024-11-05 16:39:07', '2024-11-05 17:51:58', '2', '2');
INSERT INTO `system_dict_data` VALUES (13, 'visible', '显示', '1', 1, 'success', 1, '显示', b'0', '2024-11-05 20:08:10', '2024-11-05 20:08:18', '2', '2');
INSERT INTO `system_dict_data` VALUES (14, 'visible', '隐藏', '0', 2, 'info', 1, '隐藏', b'0', '2024-11-05 20:08:58', '2024-11-05 20:08:58', '2', '2');
INSERT INTO `system_dict_data` VALUES (15, 'operateType', '查询', '1', 1, 'primary', 1, '查询', b'0', '2024-11-06 11:44:59', '2024-11-06 11:44:59', '2', '2');
INSERT INTO `system_dict_data` VALUES (16, 'operateType', '新增', '2', 2, 'success', 1, '新增', b'0', '2024-11-06 11:45:15', '2024-11-06 11:45:15', '2', '2');
INSERT INTO `system_dict_data` VALUES (17, 'operateType', '修改', '3', 3, 'primary', 1, '修改', b'0', '2024-11-06 11:45:33', '2024-11-06 11:45:33', '2', '2');
INSERT INTO `system_dict_data` VALUES (18, 'operateType', '删除', '4', 4, 'danger', 1, '删除', b'0', '2024-11-06 11:45:47', '2024-11-06 11:45:47', '2', '2');
INSERT INTO `system_dict_data` VALUES (19, 'operateType', '导出', '5', 5, 'info', 1, '导出', b'0', '2024-11-06 11:46:48', '2024-11-06 11:46:48', '2', '2');
INSERT INTO `system_dict_data` VALUES (20, 'operateType', '导入', '6', 6, 'info', 1, '导入', b'0', '2024-11-06 11:46:59', '2024-11-06 11:46:59', '2', '2');
INSERT INTO `system_dict_data` VALUES (21, 'resultCode', '成功', '0', 1, 'success', 1, '成功', b'1', '2024-11-06 13:54:04', '2024-11-06 05:55:19', '2', '2');
INSERT INTO `system_dict_data` VALUES (22, 'resultCode', '成功', '0', 1, 'success', 1, '成功', b'1', '2024-11-06 13:55:51', '2024-11-06 05:57:31', '2', '2');
INSERT INTO `system_dict_data` VALUES (23, 'resultCode', '成功', '0', 0, 'success', 1, '成功', b'0', '2024-11-06 14:03:55', '2024-11-06 14:03:55', '2', '2');
INSERT INTO `system_dict_data` VALUES (24, 'resultCode', '失败', '500', 1, 'danger', 1, '失败', b'0', '2024-11-06 14:04:25', '2024-11-06 14:04:25', '2', '2');
INSERT INTO `system_dict_data` VALUES (25, 'result', '成功', '0', 1, 'success', 1, '成功', b'0', '2024-11-06 14:21:28', '2024-11-06 14:21:28', '2', '2');
INSERT INTO `system_dict_data` VALUES (26, 'result', '账号或密码不正确', '10', 2, 'danger', 1, '账号或密码不正确', b'0', '2024-11-06 14:23:06', '2024-11-06 14:24:27', '2', '2');
INSERT INTO `system_dict_data` VALUES (27, 'result', '用户被禁用', '20', 3, 'danger', 1, '用户被禁用', b'0', '2024-11-06 14:23:25', '2024-11-06 14:23:25', '2', '2');
INSERT INTO `system_dict_data` VALUES (28, 'result', '验证码不存在', '30', 4, 'danger', 1, '验证码不存在', b'0', '2024-11-06 14:23:44', '2024-11-06 14:23:44', '2', '2');
INSERT INTO `system_dict_data` VALUES (29, 'result', '验证码不正确', '31', 5, 'danger', 1, '验证码不正确', b'0', '2024-11-06 14:23:59', '2024-11-06 14:23:59', '2', '2');
INSERT INTO `system_dict_data` VALUES (30, 'result', '未知异常', '100', 6, 'danger', 1, '未知异常', b'0', '2024-11-06 14:24:19', '2024-11-06 14:24:19', '2', '2');
INSERT INTO `system_dict_data` VALUES (31, 'loginType', '账号登录', '100', 1, 'success', 1, '账号登录', b'0', '2024-11-06 14:37:27', '2024-11-06 14:37:27', '2', '2');
INSERT INTO `system_dict_data` VALUES (32, 'loginType', '社交账号登录', '101', 2, 'warning', 1, '社交账号登录', b'0', '2024-11-06 14:37:49', '2024-11-06 14:37:49', '2', '2');
INSERT INTO `system_dict_data` VALUES (33, 'loginType', '短信登录', '103', 3, 'info', 1, '短信登录', b'0', '2024-11-06 14:38:28', '2024-11-06 14:38:28', '2', '2');
INSERT INTO `system_dict_data` VALUES (34, 'loginType', '主动登出', '200', 4, 'primary', 1, '主动登出', b'0', '2024-11-06 14:40:09', '2024-11-06 14:40:09', '2', '2');
INSERT INTO `system_dict_data` VALUES (35, 'loginType', '强制登出', '202', 5, 'danger', 1, '强制登出', b'0', '2024-11-06 14:40:28', '2024-11-06 14:40:28', '2', '2');


-- ----------------------------
-- Table structure for system_login_log
-- ----------------------------
DROP TABLE IF EXISTS `system_login_log`;
CREATE TABLE `system_login_log`  (
 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
 `log_type` bigint NOT NULL COMMENT '日志类型',
 `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户编号',
 `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
 `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '登录地点',
 `result` tinyint NOT NULL COMMENT '登陆结果',
 `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
 `user_agent` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
 `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作系统',
 `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
 `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统访问记录';
-- ----------------------------
-- Records of system_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for system_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`  (
   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
   `user_id` bigint NOT NULL COMMENT '用户编号',
   `user_type` tinyint NOT NULL DEFAULT 0 COMMENT '用户类型（1、会员, 2、管理员）',
   `module` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块标题',
   `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
   `type` bigint NOT NULL DEFAULT 0 COMMENT '操作分类（1、查询, 2、新增, 3、修改, 4、删除, 5、导出, 6、导入, 7、其他）',
   `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求方式',
   `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '请求地址',
   `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户 IP',
   `operate_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '操作地点',
   `user_agent` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '浏览器 UA',
   `java_method` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 方法名',
   `java_method_args` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'Java 方法的参数',
   `start_time` datetime NOT NULL COMMENT '操作时间',
   `duration` int NOT NULL COMMENT '执行时长',
   `result_code` int NOT NULL DEFAULT 0 COMMENT '结果码',
   `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果提示',
   `result_data` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '结果数据',
   `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
   `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录';

DROP TABLE IF EXISTS tool_data_source;
CREATE TABLE `tool_data_source` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
    `url` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
    `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
    `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
    `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据源配置表';
-- ----------------------------
-- Records of tool_data_source
-- ----------------------------
INSERT INTO `tool_data_source` VALUES (1, 'master', 'jdbc:mysql://139.9.202.135:1653/wick_boot?useUnicode=true&characterEncoding=UTF-8&useSSL=false', 'root', 'P@ssw0rd2024', b'1', '2024-07-23 10:28:30', '2024-10-12 08:48:54', '2', '2');
INSERT INTO `tool_data_source` VALUES (2, 'wick-boot-master', 'jdbc:mysql://139.9.202.135:1653/wick_boot?useUnicode=true&characterEncoding=UTF-8&useSSL=false', 'root', 'P@ssw0rd2024', b'0', '2024-10-11 14:15:19', '2024-10-25 01:56:34', '2', '2');

DROP TABLE IF EXISTS tool_code_gen_table;
CREATE TABLE tool_code_gen_table
(
    `id`                bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '编号',
    `data_source_id`    bigint                                                        NOT NULL COMMENT '数据源ID',
    `table_name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' comment '表名称',
    `table_comment`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' comment '表描述',
    `sub_table_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL comment '关联子表的表名',
    `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL comment '子表关联的外键名',
    `class_name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' comment '实体类名称',
    `tpl_category`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'crud' comment '使用的模板（crud单表操作 tree树表操作）',
    `tpl_web_type`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL DEFAULT '' comment '前端模板类型（element-ui模版 element-plus模版）',
    `package_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL comment '生成包路径',
    `module_name`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL comment '生成模块名',
    `business_name`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL comment '生成业务名',
    `function_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL comment '生成功能名',
    `function_author`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL comment '生成功能作者',
    `gen_type`          char(1)                                                       NOT NULL DEFAULT '0' comment '生成代码方式（0zip压缩包 1自定义路径）',
    `parent_menu_id`    int NULL DEFAULT NULL COMMENT '父菜单ID',
    `gen_path`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '/' comment '生成路径（不填默认项目路径）',
    `options`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL comment '其它生成选项',
    `remark`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL comment '备注',
    `deleted`           bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `create_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `update_by`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成业务表';
-- ----------------------------
-- Records of tool_code_gen_table
-- ----------------------------

DROP TABLE IF EXISTS tool_code_gen_table_column;
CREATE TABLE `tool_code_gen_table_column` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(20) NOT NULL COMMENT '归属表编号',
  `column_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '列名称',
  `column_comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '列描述',
  `column_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '列类型',
  `java_type` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'JAVA类型',
  `java_field` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'JAVA字段名',
  `is_pk` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否主键（1是）',
  `is_increment` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否自增（1是）',
  `is_required` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否必填（1是）',
  `is_insert` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否编辑字段（1是）',
  `is_list` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否列表字段（1是）',
  `is_query` char(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT '0' COMMENT '显示顺序',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_table_id` (`table_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成业务表字段';
-- ----------------------------
-- Records of tool_code_gen_table_column
-- ----------------------------

BEGIN;
COMMIT;