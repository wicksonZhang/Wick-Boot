package com.wick.boot.module.system.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.role.SystemRolePermsDTO;
import com.wick.boot.module.system.model.entity.SystemRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 后台管理 - 角色-菜单 Mapper
 */
@Mapper
public interface SystemRoleMenuMapper extends BaseMapperX<SystemRoleMenu> {

    /**
     * 通过 roleCodes 获取角色-权限菜单
     *
     * @param roleCodes 角色Code集合
     * @return List<SystemRolePermsDTO>
     */
    List<SystemRolePermsDTO> selectRolePermsList(@Param("roleCodes") Set<String> roleCodes);

    /**
     * 删除 角色-权限菜单 信息
     *
     * @param roleIds 角色Ids
     */
    void deleteRolePermsByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * 删除 角色-权限菜单 信息
     *
     * @param menuIds 菜单Ids
     */
    void deleteRolePermsByMenuIds(@Param("menuIds") List<Long> menuIds);

}
