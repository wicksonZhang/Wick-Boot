package com.wick.module.system.mapper;

import com.wick.module.system.model.dto.SystemRolePermsDTO;
import com.wick.module.system.model.entity.SystemRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 后台管理 - 角色-菜单 Mapper
 */
@Mapper
public interface ISystemRoleMenuMapper extends BaseMapper<SystemRoleMenu> {

    /**
     * 通过 roleCode 获取角色-权限菜单
     *
     * @param roleCode 角色Code
     * @return List<SystemRolePermsDTO>
     */
    List<SystemRolePermsDTO> selectRolePermsList(String roleCode);
}
