package com.wick.boot.module.system.app.service;

import java.util.List;
import java.util.Set;

/**
 * 角色-菜单 业务层
 */
public interface ISystemRoleMenuService {

    /**
     * 删除角色-权限信息
     *
     * @param roleIds 角色Ids
     */
    void deleteRolePermsByRoleId(List<Long> roleIds);

    /**
     * 删除角色-权限信息
     *
     * @param menuIds 菜单Ids
     */
    void deleteRolePermsByMenuId(List<Long> menuIds);

    /**
     * 刷新角色-权限信息
     */
    void refreshRolePermsCache();

    /**
     * 刷新角色-权限信息
     *
     * @param codes 角色 code 集合
     */
    void refreshRolePermsCache(Set<String> codes);

}
