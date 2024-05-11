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
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.user.AddUserVO;

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
    private ISystemUserMapper userMapper;

    @Resource
    private ISystemRoleMapper roleMapper;

    @Resource
    private ISystemDeptMapper deptMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(AddUserVO reqVO) {
        // 验证用户名称是否存在
        this.validateUsername(reqVO.getUsername());
        // 验证所属部门是否存在
        this.validateDeptId(reqVO.getDeptId());
        // 验证角色是否存在
        this.validateRoleIds(reqVO.getRoleIds());
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

    private void validateRoleIds(List<Long> roleIds) {
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

}
