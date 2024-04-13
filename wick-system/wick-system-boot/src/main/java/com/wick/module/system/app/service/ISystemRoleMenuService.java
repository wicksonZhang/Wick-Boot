package com.wick.module.system.app.service;

/**
 * 角色-菜单 业务层
 */
public interface ISystemRoleMenuService {

    /**
     * 刷新角色-权限信息
     */
    void refreshRolePermsCache();

}
