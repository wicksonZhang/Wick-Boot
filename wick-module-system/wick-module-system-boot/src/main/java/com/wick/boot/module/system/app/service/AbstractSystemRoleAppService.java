package com.wick.boot.module.system.app.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ParameterException;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.ISystemRoleMapper;
import com.wick.boot.module.system.mapper.ISystemRoleMenuMapper;
import com.wick.boot.module.system.mapper.ISystemUserRoleMapper;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理-防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
public abstract class AbstractSystemRoleAppService {

    @Resource
    protected ISystemRoleMapper roleMapper;

    @Resource
    protected ISystemUserRoleMapper userRoleMapper;

    @Resource
    protected ISystemRoleMenuMapper roleMenuMapper;

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

    // ============================================== 删除参数校验 ==============================================

    protected void validateDeleteParams(List<SystemRole> systemRoleList, List<Long> ids) {
        // 验证角色是否存在
        this.validateRoleList(systemRoleList);
        // 验证角色集合和 ids 是否匹配
        this.validateRoleByIds(systemRoleList, ids);
        // 验证角色集合中是否包含 Root 角色
        this.validateRoleByRoot(systemRoleList);
        // 验证当前角色是否与用户关联
        this.validateRoleByUser(systemRoleList, ids);
    }

    /**
     * 验证角色是否存在
     *
     * @param systemRoleList 角色集合
     */
    private void validateRoleList(List<SystemRole> systemRoleList) {
        // 校验角色集合是否存在
        if (CollUtil.isEmpty(systemRoleList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.MENU_NOT_EXIST);
        }
    }

    /**
     * 验证角色集合和 ids 是否匹配
     *
     * @param systemRoleList 角色集合
     * @param ids            角色Ids
     */
    private void validateRoleByIds(List<SystemRole> systemRoleList, List<Long> ids) {
        // 校验不存在的角色ID
        List<Long> menuIds = systemRoleList.stream().map(SystemRole::getId).collect(Collectors.toList());
        // 验证是否存在
        Collection<Long> errorIds = CollectionUtil.subtract(ids, menuIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认角色主键 " + errorIds + " 是否存在";
            throw ServiceException.getInstance(ErrorCodeSystem.MENU_NOT_EXIST.getCode(), errorMsg);
        }
    }

    /**
     * 验证角色集合中是否包含 Root 角色
     *
     * @param systemRoleList 角色集合
     */
    private void validateRoleByRoot(List<SystemRole> systemRoleList) {
        List<String> codes = systemRoleList.stream()
                .map(SystemRole::getCode)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        boolean containsRoot = CollectionUtil.contains(codes, GlobalConstants.ROOT_ROLE_CODE);
        if (containsRoot) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "ROOT 角色不能被删除");
        }
    }

    /**
     * 验证当前角色是否与用户关联
     *
     * @param roleList 角色集合
     * @param ids      角色Ids
     */
    private void validateRoleByUser(List<SystemRole> roleList, List<Long> ids) {
        List<SystemUserRole> userRoles = this.userRoleMapper.selectUserRoleByRoleIds(ids);
        if (CollUtil.isEmpty(userRoles)) {
            return;
        }
        List<String> roleNames = roleList.stream().map(SystemRole::getName).collect(Collectors.toList());
        String errorMsg = "角色 " + roleNames + " 与用户存在关联，请先解除关联后删除";
        throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, errorMsg);
    }

    // ======================================= 校验 分配菜单(包括按钮权限)给角色 =======================================

    protected void validateAssignParams(SystemRole systemRole) {
        // 验证角色是否存在
        this.validateRole(systemRole);
        // 校验是否是ROOT
        this.validateRoleByRoot(systemRole.getCode());
    }

    private void validateRole(SystemRole systemRole) {
        if (ObjUtil.isNull(systemRole)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "参数错误，角色Id不存在");
        }
    }

    private void validateRoleByRoot(String code) {
        if (GlobalConstants.ROOT_ROLE_CODE.equals(code)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "ROOT 角色不能被修改");
        }
    }

}
