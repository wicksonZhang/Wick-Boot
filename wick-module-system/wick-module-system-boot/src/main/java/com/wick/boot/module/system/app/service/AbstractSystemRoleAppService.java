package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ParameterException;
import com.wick.boot.module.system.mapper.ISystemRoleMapper;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;

import javax.annotation.Resource;

/**
 * 角色管理-防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
public abstract class AbstractSystemRoleAppService {

    @Resource
    protected ISystemRoleMapper roleMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(AddRoleVo reqVO) {
        // 校验角色名称
        this.validateRoleByName(reqVO.getName());
        // 校验角色编码
        this.validateRoleByCode(reqVO.getCode());
    }

    /**
     * 校验角色名称
     *
     * @param name 角色名称
     */
    private void validateRoleByName(String name) {
        SystemRole systemRole = this.roleMapper.selectRoleByName(name);
        if (ObjUtil.isNotNull(systemRole)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "角色名称已存在");
        }
    }

    /**
     * 校验角色编码
     *
     * @param code 角色编码
     */
    private void validateRoleByCode(String code) {
        SystemRole systemRole = this.roleMapper.selectRoleByCode(code);
        if (ObjUtil.isNotNull(systemRole)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "角色编码已存在");
        }
    }

    // ============================================== 编辑参数校验 ==============================================

    protected void validateUpdateParams(UpdateRoleVo reqVO) {
        // 验证角色信息是否存在
        SystemRole systemRole = this.validateRoleById(reqVO.getId());
        // 校验角色名称
        this.validateRoleByName(systemRole.getName(), reqVO.getName());
        // 校验角色编码
        this.validateRoleByCode(systemRole.getCode(), reqVO.getCode());
    }

    /**
     * 验证角色信息是否存在
     *
     * @param id 角色Id
     */
    private SystemRole validateRoleById(Long id) {
        SystemRole systemRole = this.roleMapper.selectById(id);
        if (ObjUtil.isNull(systemRole)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "角色Id不存在");
        }
        return systemRole;
    }

    /**
     * 校验角色名称
     *
     * @param oldName 旧角色名称
     * @param newName 新角色名称
     */
    private void validateRoleByName(String oldName, String newName) {
        if (oldName.equals(newName)) {
            return;
        }
        this.validateRoleByName(newName);
    }

    /**
     * 校验角色编码
     *
     * @param oldCode 旧角色编码
     * @param newCode 新角色编码
     */
    private void validateRoleByCode(String oldCode, String newCode) {
        if (oldCode.equals(newCode)) {
            return;
        }
        this.validateRoleByCode(newCode);
    }

}
