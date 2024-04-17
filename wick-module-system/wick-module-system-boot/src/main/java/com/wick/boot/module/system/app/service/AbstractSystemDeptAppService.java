package com.wick.boot.module.system.app.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.mapper.ISystemDeptMapper;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.vo.dept.AddDeptReqVO;

import javax.annotation.Resource;

/**
 * 部门管理 - 防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-04-17
 */
public abstract class AbstractSystemDeptAppService {

    @Resource
    protected ISystemDeptMapper systemDeptMapper;

    // ============================================== 新增参数校验 ==============================================

    protected void validateAddParams(AddDeptReqVO reqVO) {
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

}
