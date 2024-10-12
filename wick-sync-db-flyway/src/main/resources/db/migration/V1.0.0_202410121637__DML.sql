-- 菜单SQL
INSERT INTO`system_menu`(`parent_id`,`type`,`name`,`route_name`,`route_path`,`component`,`perm`,`icon`,`sort`,`visible`,`redirect`,`tree_path`,`always_show`,`keep_alive`,`deleted`,`create_time`,`update_time`,`create_by`,`update_by`)
VALUES('54',1,'数据源配置','data-source','data-source','tool/data-source/index',NULL,'',1,1,NULL,'0,54',NULL,1,b'0',sysdate(),sysdate(),1,1);

-- 按钮父菜单ID
SELECT @parentId:=LAST_INSERT_ID();

-- 按钮SQL
INSERT INTO `system_menu`(`parent_id`,`type`,`name`,`route_name`,`route_path`,`component`,`perm`,`icon`,`sort`,`visible`,`redirect`,`tree_path`,`always_show`,`keep_alive`,`deleted`,`create_time`,`update_time`,`create_by`,`update_by`)
VALUES(@parentId,4,'数据源配置新增',NULL,'',NULL,'tool:data-source:add',NULL,1,1,NULL,CONCAT('0,','54,',@parentId),NULL,NULL,b'0',sysdate(),sysdate(),1,1);

INSERT INTO `system_menu`(`parent_id`,`type`,`name`,`route_name`,`route_path`,`component`,`perm`,`icon`,`sort`,`visible`,`redirect`,`tree_path`,`always_show`,`keep_alive`,`deleted`,`create_time`,`update_time`,`create_by`,`update_by`)
VALUES(@parentId,4,'数据源配置编辑',NULL,'',NULL,'tool:data-source:update',NULL,2,1,NULL,CONCAT('0,','54,',@parentId),NULL,NULL,b'0',sysdate(),sysdate(),1,1);

INSERT INTO `system_menu`(`parent_id`,`type`,`name`,`route_name`,`route_path`,`component`,`perm`,`icon`,`sort`,`visible`,`redirect`,`tree_path`,`always_show`,`keep_alive`,`deleted`,`create_time`,`update_time`,`create_by`,`update_by`)
VALUES(@parentId,4,'数据源配置删除',NULL,'',NULL,'tool:data-source:delete',NULL,3,1,NULL,CONCAT('0,','54,',@parentId),NULL,NULL,b'0',sysdate(),sysdate(),1,1);

INSERT INTO `system_menu`(`parent_id`,`type`,`name`,`route_name`,`route_path`,`component`,`perm`,`icon`,`sort`,`visible`,`redirect`,`tree_path`,`always_show`,`keep_alive`,`deleted`,`create_time`,`update_time`,`create_by`,`update_by`)
VALUES(@parentId,4,'获取数据源配置数据',NULL,'',NULL,'tool:data-source:query',NULL,4,1,NULL,CONCAT('0,','54,',@parentId),NULL,NULL,b'0',sysdate(),sysdate(),1,1);

INSERT INTO `system_menu`(`parent_id`,`type`,`name`,`route_name`,`route_path`,`component`,`perm`,`icon`,`sort`,`visible`,`redirect`,`tree_path`,`always_show`,`keep_alive`,`deleted`,`create_time`,`update_time`,`create_by`,`update_by`)
VALUES(@parentId,4,'获取数据源配置分页',NULL,'',NULL,'tool:data-source:query',NULL,4,1,NULL,CONCAT('0,','54,',@parentId),NULL,NULL,b'0',sysdate(),sysdate(),1,1);