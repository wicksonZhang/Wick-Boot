package com.wick.boot.module.system.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.mapper.SystemDeptMapper;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.vo.dept.SystemDeptAddVO;
import com.wick.boot.module.system.model.vo.dept.SystemDeptUpdateVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 - 防腐层
 *
 * @author Wickson
 * @date 2024-04-17
 */
public abstract class SystemDeptAbstractService {

    @Resource
    protected SystemDeptMapper systemDeptMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(SystemDeptAddVO reqVO) {
        // 校验上级部门是否存在
        this.validateDeptByParentId(reqVO.getParentId());
        // 校验部门名称是否存在
        this.validateDeptByName(reqVO.getParentId(), reqVO.getName());
    }

    /**
     * 校验上级部门
     *
     * @param parentId 父级ID
     */
    private void validateDeptByParentId(Long parentId) {
        // 根节点不进行校验
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            return;
        }
        // 通过 parentId 获取部门信息
        SystemDept systemDept = this.systemDeptMapper.selectById(parentId);
        if (ObjUtil.isNull(systemDept)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DEPT_NOT_EXIST);
        }
    }

    /**
     * 同一个父级部门下不能存在两个相同的部门名称
     *
     * @param parentId 父级部门ID
     * @param name     部门名称
     */
    private void validateDeptByName(Long parentId, String name) {
        Long count = this.systemDeptMapper.selectCountByParentIdAndName(parentId, name);
        if (count > 0) {
            throw ServiceException.getInstance(ErrorCodeSystem.DEPT_NAME_ALREADY_EXIST);
        }
    }

    // ============================================== 更新参数校验 ==============================================

    protected void validateUpdateParams(SystemDeptUpdateVO reqVO) {
        // 校验部门是否存在
        SystemDept systemDept = this.validateDeptById(reqVO.getId());
        // 校验上级部门ID
        this.validateDeptByParentId(systemDept.getParentId(), reqVO.getParentId(), reqVO.getId());
        // 校验部门名称
        this.validateDeptByName(systemDept.getName(), reqVO.getParentId(), reqVO.getName());
    }

    /**
     * 校验当前部门是否存在
     *
     * @param id 部门ID
     */
    private SystemDept validateDeptById(Long id) {
        SystemDept systemDept = this.systemDeptMapper.selectById(id);
        if (ObjUtil.isNull(systemDept)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DEPT_NOT_EXIST);
        }
        return systemDept;
    }

    /**
     * 校验上级部门不能是自己
     *
     * @param oldParentId 旧父级ID
     * @param newParentId 新父级ID
     * @param deptId      部门ID
     */
    private void validateDeptByParentId(Long oldParentId, Long newParentId, Long deptId) {
        if (oldParentId.equals(newParentId)) {
            return;
        }
        if (newParentId.equals(deptId)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DEPT_PARENT_ID_CANNOT_BE_SELF);
        }
        this.validateDeptByParentId(newParentId);
    }

    /**
     * 校验部门名称
     *
     * @param oldName     旧部门名称
     * @param newParentId 新父级部门ID
     * @param newName     新部门名称
     */
    private void validateDeptByName(String oldName, Long newParentId, String newName) {
        if (oldName.equals(newName)) {
            return;
        }
        this.validateDeptByName(newParentId, newName);
    }

    // ============================================== 删除参数校验 ==============================================

    protected void validateDeleteParams(List<SystemDept> systemDeptList, List<Long> ids) {
        // 验证部门是否存在
        this.validateDeptList(systemDeptList);
        // 验证部门集合和 ids 是否匹配
        this.validateDeptByIds(systemDeptList, ids);
    }

    private void validateDeptList(List<SystemDept> systemDeptList) {
        // 校验部门集合是否存在
        if (CollUtil.isEmpty(systemDeptList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.DEPT_NOT_EXIST);
        }
    }

    private void validateDeptByIds(List<SystemDept> systemDeptList, List<Long> ids) {
        // 校验不存在的部门ID
        List<Long> deptIds = systemDeptList.stream().map(SystemDept::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, deptIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认部门主键 " + errorIds + " 是否存在";
            throw ServiceException.getInstance(ErrorCodeSystem.DEPT_NOT_EXIST.getCode(), errorMsg);
        }
    }

}
