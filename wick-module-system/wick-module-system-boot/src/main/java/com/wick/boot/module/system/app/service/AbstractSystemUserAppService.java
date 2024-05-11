package com.wick.boot.module.system.app.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ParameterException;
import com.wick.boot.module.system.mapper.ISystemDeptMapper;
import com.wick.boot.module.system.mapper.ISystemRoleMapper;
import com.wick.boot.module.system.mapper.ISystemUserMapper;
import com.wick.boot.module.system.mapper.ISystemUserRoleMapper;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import com.wick.boot.module.system.model.vo.user.AddUserVO;
import com.wick.boot.module.system.model.vo.user.UpdateUserVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 新增用户信息-防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-05-11
 */
public abstract class AbstractSystemUserAppService {

    @Resource
    protected ISystemUserMapper userMapper;

    @Resource
    private ISystemRoleMapper roleMapper;

    @Resource
    private ISystemDeptMapper deptMapper;

    @Resource
    protected ISystemUserRoleMapper userRoleMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(AddUserVO reqVO) {
        // 验证用户名称是否存在
        this.validateUsername(reqVO.getUsername());
        // 验证所属部门是否存在
        this.validateDeptId(reqVO.getDeptId());
        // 验证角色是否存在
        this.validateRoleIds(reqVO.getRoleIds());
        // 验证手机号是否唯一
        this.validateMobile(reqVO.getMobile());
        // 验证邮箱是否唯一
        this.validateEmail(reqVO.getEmail());
    }

    private void validateUsername(String username) {
        SystemUser systemUser = userMapper.selectByUsername(username);
        if (ObjUtil.isNotNull(systemUser)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "用户名称已存在");
        }
    }

    private void validateDeptId(Long deptId) {
        SystemDept systemDept = deptMapper.selectById(deptId);
        if (ObjUtil.isNull(systemDept)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "当前部门不存在");
        }
    }

    private void validateRoleIds(Set<Long> roleIds) {
        List<SystemRole> systemRoles = this.roleMapper.selectBatchIds(roleIds);
        if (CollUtil.isEmpty(systemRoles)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "当前角色不存在");
        }
        Set<Long> ids = systemRoles.stream().map(SystemRole::getId).collect(Collectors.toSet());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, roleIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认角色主键 " + errorIds + " 是否存在";
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, errorMsg);
        }
        Set<String> codes = systemRoles.stream().map(SystemRole::getCode).collect(Collectors.toSet());
        boolean isRoot = CollectionUtil.contains(codes, GlobalConstants.ROOT_ROLE_CODE);
        if (isRoot) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "新增角色不能是 Root");
        }
    }

    private void validateMobile(String mobile) {
        Long count = this.userMapper.selectCountByMobile(mobile);
        if (count > 0) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "手机号已存在");
        }
    }

    private void validateEmail(String email) {
        Long count = this.userMapper.selectCountByEmail(email);
        if (count > 0) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "邮箱已存在");
        }
    }

    // ============================================== 编辑参数校验 ==============================================

    protected void validateUpdateParams(UpdateUserVO reqVO) {
        SystemUser systemUser = getUserById(reqVO.getId());
        // 验证用户信息是否存在
        this.validateUser(systemUser);
        // 验证用户名称是否存在
        this.validateUsername(systemUser.getUsername(), reqVO.getUsername());
        // 验证所属部门是否存在
        this.validateDeptId(systemUser.getDeptId(), reqVO.getDeptId());
        // 验证角色是否存在
        this.validateRoleIds(systemUser.getId(), reqVO.getRoleIds());
        // 验证手机号是否唯一
        this.validateMobile(systemUser.getMobile(), reqVO.getMobile());
        // 验证邮箱是否唯一
        this.validateEmail(systemUser.getEmail(), reqVO.getEmail());
    }

    /**
     * 获取用户信息
     *
     * @param id 用户Id
     * @return 用户信息
     */
    private SystemUser getUserById(Long id) {
        return this.userMapper.selectById(id);
    }

    /**
     * 验证用户信息是否存在
     *
     * @param systemUser 用户信息
     */
    private void validateUser(SystemUser systemUser) {
        if (ObjUtil.isNull(systemUser)) {
            throw ParameterException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID, "用户信息不存在");
        }
    }

    /**
     * 验证用户名称是否存在
     *
     * @param sourceUsername 原名称
     * @param targetUsername 目标名称
     */
    private void validateUsername(String sourceUsername, String targetUsername) {
        if (sourceUsername.equals(targetUsername)) {
            return;
        }
        this.validateUsername(targetUsername);
    }

    /**
     * 验证所属部门是否存在
     *
     * @param sourceDeptId 原部门Id
     * @param targetDeptId 目标部门Id
     */
    private void validateDeptId(Long sourceDeptId, Long targetDeptId) {
        if (sourceDeptId.equals(targetDeptId)) {
            return;
        }
        this.validateDeptId(targetDeptId);
    }

    /**
     * 验证角色是否存在
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    private void validateRoleIds(Long userId, Set<Long> roleIds) {
        List<SystemUserRole> userRoles = this.userRoleMapper.selectListByUserId(userId);
        Set<Long> ids = userRoles.stream().map(SystemUserRole::getRoleId).collect(Collectors.toSet());
        Collection<Long> subtract = CollectionUtil.subtract(roleIds, ids);
        if (CollectionUtil.isEmpty(subtract)) {
            return;
        }
        this.validateRoleIds(roleIds);
    }

    private void validateMobile(String sourceMobile, String targetMobile) {
        if (sourceMobile.equals(targetMobile)) {
            return;
        }
        this.validateMobile(targetMobile);
    }

    private void validateEmail(String sourceEmail, String targetEmail) {
        if (sourceEmail.equals(targetEmail)) {
            return;
        }
        this.validateEmail(targetEmail);
    }

}
