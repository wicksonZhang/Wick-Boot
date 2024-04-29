package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.QueryRolePageReqVO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;

/**
 * 角色管理-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
public interface ISystemRoleService {

    /**
     * 新增角色信息
     *
     * @param reqVO 新增角色请求参数
     * @return 角色主键Id
     */
    Long addRole(AddRoleVo reqVO);

    /**
     * 编辑角色信息
     *
     * @param reqVO 编辑角色请求参数
     */
    void updateRole(UpdateRoleVo reqVO);

    /**
     * 角色分页
     *
     * @param reqVO 请求参数
     * @return PageResult<SystemRoleDTO>
     */
    PageResult<SystemRoleDTO> getRolePage(QueryRolePageReqVO reqVO);

}
