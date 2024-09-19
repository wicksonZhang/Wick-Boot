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
INSERT INTO `system_menu` VALUES (1, 0, 2, '系统管理','', '/system', 'Layout', NULL, 'system', 1, 1, '/system/user', '0', NULL, NULL, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (2, 1, 1, '用户管理', 'User', 'user', 'system/user/index', NULL, 'user', 1, 1, NULL, '0,1', NULL, 1, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (3, 1, 1, '角色管理', 'Role', 'role', 'system/role/index', NULL, 'role', 2, 1, NULL, '0,1', NULL, 1, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (4, 1, 1, '菜单管理', 'Menu', 'menu', 'system/menu/index', NULL, 'menu', 3, 1, NULL, '0,1', NULL, 1, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (5, 1, 1, '部门管理', 'Dept', 'dept', 'system/dept/index', NULL, 'tree', 4, 1, NULL, '0,1', NULL, 1, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (6, 1, 1, '字典管理', 'Dict', 'dict', 'system/dict/index', NULL, 'dict', 5, 1, NULL, '0,1', NULL, 1, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (40, 2, 4, '新增用户', NULL, '', NULL, 'system:user:add', '', 1, 1, '', '0,1,2', NULL, NULL, 0, '2021-08-28 09:12:21', '2021-08-28 09:12:21', 1, 1);
INSERT INTO `system_menu` VALUES (41, 2, 4, '修改用户', NULL, '', NULL, 'system:user:update', '', 2, 1, '', '0,1,2', NULL, NULL, 0, '2022-11-05 01:26:44', '2022-11-05 01:26:44', 1, 1);
INSERT INTO `system_menu` VALUES (42, 2, 4, '删除用户', NULL, '', NULL, 'system:user:delete', '', 3, 1, '', '0,1,2', NULL, NULL, 0, '2022-11-05 01:27:13', '2022-11-05 01:27:13', 1, 1);
INSERT INTO `system_menu` VALUES (69, 3, 4, '角色新增', NULL, '', NULL, 'system:role:add', '', 1, 1, NULL, '0,1,3', NULL, NULL, 0, '2023-05-20 23:39:09', '2023-05-20 23:39:09', 1, 1);
INSERT INTO `system_menu` VALUES (70, 3, 4, '角色编辑', NULL, '', NULL, 'system:role:update', '', 2, 1, NULL, '0,1,3', NULL, NULL, 0, '2023-05-20 23:40:31', '2023-05-20 23:40:31', 1, 1);
INSERT INTO `system_menu` VALUES (71, 3, 4, '角色删除', NULL, '', NULL, 'system:role:delete', '', 3, 1, NULL, '0,1,3', NULL, NULL, 0, '2023-05-20 23:41:08', '2023-05-20 23:41:08', 1, 1);
INSERT INTO `system_menu` VALUES (72, 3, 4, '分配权限', NULL, '', NULL, 'system:role:assign', '', 3, 1, NULL, '0,1,3', NULL, NULL, 0, '2023-05-20 23:41:08', '2023-05-20 23:41:08', 1, 1);
INSERT INTO `system_menu` VALUES (73, 4, 4, '菜单新增', NULL, '', NULL, 'system:menu:add', '', 1, 1, NULL, '0,1,4', NULL, NULL, 0, '2023-05-20 23:41:35', '2023-05-20 23:41:35', 1, 1);
INSERT INTO `system_menu` VALUES (74, 4, 4, '菜单编辑', NULL, '', NULL, 'system:menu:update', '', 3, 1, NULL, '0,1,4', NULL, NULL, 0, '2023-05-20 23:41:58', '2023-05-20 23:41:58', 1, 1);
INSERT INTO `system_menu` VALUES (75, 4, 4, '菜单删除', NULL, '', NULL, 'system:menu:delete', '', 3, 1, NULL, '0,1,4', NULL, NULL, 0, '2023-05-20 23:44:18', '2023-05-20 23:44:18', 1, 1);
INSERT INTO `system_menu` VALUES (76, 5, 4, '部门新增', NULL, '', NULL, 'system:dept:add', '', 1, 1, NULL, '0,1,5', NULL, NULL, 0, '2023-05-20 23:45:00', '2023-05-20 23:45:00', 1, 1);
INSERT INTO `system_menu` VALUES (77, 5, 4, '部门编辑', NULL, '', NULL, 'system:dept:update', '', 2, 1, NULL, '0,1,5', NULL, NULL, 0, '2023-05-20 23:46:16', '2023-05-20 23:46:16', 1, 1);
INSERT INTO `system_menu` VALUES (78, 5, 4, '部门删除', NULL, '', NULL, 'system:dept:delete', '', 3, 1, NULL, '0,1,5', NULL, NULL, 0, '2023-05-20 23:46:36', '2023-05-20 23:46:36', 1, 1);
INSERT INTO `system_menu` VALUES (79, 6, 4, '字典类型新增', NULL, '', NULL, 'system:dict_type:add', '', 1, 1, NULL, '0,1,6', NULL, NULL, 0, '2023-05-21 00:16:06', '2023-05-21 00:16:06', 1, 1);
INSERT INTO `system_menu` VALUES (81, 6, 4, '字典类型编辑', NULL, '', NULL, 'system:dict_type:update', '', 2, 1, NULL, '0,1,6', NULL, NULL, 0, '2023-05-21 00:27:37', '2023-05-21 00:27:37', 1, 1);
INSERT INTO `system_menu` VALUES (84, 6, 4, '字典类型删除', NULL, '', NULL, 'system:dict_type:delete', '', 3, 1, NULL, '0,1,6', NULL, NULL, 0, '2023-05-21 00:29:39', '2023-05-21 00:29:39', 1, 1);
INSERT INTO `system_menu` VALUES (85, 6, 4, '字典数据新增', NULL, '', NULL, 'system:dict_data:add', '', 4, 1, NULL, '0,1,6', NULL, NULL, 0, '2023-05-21 00:46:56', '2023-05-21 00:47:06', 1, 1);
INSERT INTO `system_menu` VALUES (86, 6, 4, '字典数据编辑', NULL, '', NULL, 'system:dict_data:update', '', 5, 1, NULL, '0,1,6', NULL, NULL, 0, '2023-05-21 00:47:36', '2023-05-21 00:47:36', 1, 1);
INSERT INTO `system_menu` VALUES (87, 6, 4, '字典数据删除', NULL, '', NULL, 'system:dict_data:delete', '', 6, 1, NULL, '0,1,6', NULL, NULL, 0, '2023-05-21 00:48:10', '2023-05-21 00:48:20', 1, 1);
INSERT INTO `system_menu` VALUES (88, 2, 4, '重置密码', NULL, '', NULL, 'system:user:reset_pwd', '', 4, 1, NULL, '0,1,2', NULL, NULL, 0, '2023-05-21 00:49:18', '2023-05-21 00:49:18', 1, 1);
INSERT INTO `system_menu` VALUES (89, 1, 1, '日志管理', '', '/logger', 'system/logger/index', NULL, 'menu', 6, 1, '/system/logger/login-log', '0,1', NULL, 1, b'0', '2024-06-25 10:14:37', '2024-06-25 17:07:11', '2', '2');
INSERT INTO `system_menu` VALUES (90, 89, 1, '登录日志', 'Login', 'login-log', 'system/logger/login-log/index', NULL, 'user', 1, 1, '', '0,1,92', 0, 0, b'0', '2024-06-25 11:19:43', '2024-06-25 16:32:47', '2', '2');
INSERT INTO `system_menu` VALUES (91, 89, 1, '操作日志', 'Operate', 'operate-log', 'system/logger/operate-log/index', NULL, 'document', 1, 1, '', '0,1,92', NULL, NULL, b'0', '2024-06-25 16:34:34', '2024-06-25 16:35:14', '2', '2');

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
INSERT INTO `system_role_menu` VALUES (2, 40);
INSERT INTO `system_role_menu` VALUES (2, 41);
INSERT INTO `system_role_menu` VALUES (2, 42);
INSERT INTO `system_role_menu` VALUES (2, 69);
INSERT INTO `system_role_menu` VALUES (2, 70);
INSERT INTO `system_role_menu` VALUES (2, 71);
INSERT INTO `system_role_menu` VALUES (2, 72);
INSERT INTO `system_role_menu` VALUES (2, 73);
INSERT INTO `system_role_menu` VALUES (2, 74);
INSERT INTO `system_role_menu` VALUES (2, 75);
INSERT INTO `system_role_menu` VALUES (2, 76);
INSERT INTO `system_role_menu` VALUES (2, 77);
INSERT INTO `system_role_menu` VALUES (2, 78);
INSERT INTO `system_role_menu` VALUES (2, 79);
INSERT INTO `system_role_menu` VALUES (2, 81);
INSERT INTO `system_role_menu` VALUES (2, 84);
INSERT INTO `system_role_menu` VALUES (2, 85);
INSERT INTO `system_role_menu` VALUES (2, 86);
INSERT INTO `system_role_menu` VALUES (2, 87);
INSERT INTO `system_role_menu` VALUES (2, 88);
INSERT INTO `system_role_menu` VALUES (2, 89);
INSERT INTO `system_role_menu` VALUES (2, 90);
INSERT INTO `system_role_menu` VALUES (2, 91);


-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '类型名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '类型编码',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态(1:正常;0:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `system_dict_type` VALUES (1, '性别', 'gender', 1, NULL, 0, '2019-12-06 19:03:32', '2022-06-12 16:21:28', 1, 1);


-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `color_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '颜色类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT 'css 样式',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `system_dict_data` VALUES (1, 'gender', '男', '1', 1, 0, 'default', 'A', '性别男', 0, '2019-05-05 13:07:52', '2022-06-12 23:20:39', 1, 1);
INSERT INTO `system_dict_data` VALUES (2, 'gender', '女', '2', 2, 0, 'success', '', '性别女', 0, '2019-04-19 11:33:00', '2019-07-02 14:23:05', 1, 1);
INSERT INTO `system_dict_data` VALUES (3, 'gender', '未知', '0', 3, 0, 'success', '', '未知性别', 0, '2020-10-17 08:09:31', '2020-10-17 08:09:31', 1, 1);


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

-- ----------------------------
-- Records of system_operate_log
-- ----------------------------
-- 新增系统工具
INSERT INTO `system_menu` (`id`, `parent_id`, `type`, `name`, `route_name`, `route_path`, `component`, `perm`, `icon`, `sort`, `visible`, `redirect`, `tree_path`, `always_show`, `keep_alive`, `deleted`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES (92, 0, 2, '系统工具', NULL, '/tool', 'Layout', NULL, 'menu', 2, 1, '', '0', 1, 1, b'0', '2024-09-19 15:38:40', '2024-09-19 15:38:40', '2', '2');
INSERT INTO `system_menu` (`id`, `parent_id`, `type`, `name`, `route_name`, `route_path`, `component`, `perm`, `icon`, `sort`, `visible`, `redirect`, `tree_path`, `always_show`, `keep_alive`, `deleted`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES (93, 92, 1, '代码生成', 'Codegen', 'codegen', 'tools/codeGen/index', NULL, 'code', 1, 1, '', '0,92', 0, 1, b'0', '2024-09-19 15:39:46', '2024-09-19 15:39:46', '2', '2');
-- 新增对应角色信息
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (2, 92);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (2, 93);

