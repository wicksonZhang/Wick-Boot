package com.wick.boot.module.system.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.SystemRolePermsDTO;
import com.wick.boot.module.system.model.entity.SystemRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 后台管理 - 角色-菜单 Mapper
 */
@Mapper
public interface ISystemRoleMenuMapper extends BaseMapperX<SystemRoleMenu> {

    /**
     * 通过 roleCode 获取角色-权限菜单
     *
     * @param roleCode 角色Code
     * @return List<SystemRolePermsDTO>
     */
    List<SystemRolePermsDTO> selectRolePermsList(String roleCode);
}
