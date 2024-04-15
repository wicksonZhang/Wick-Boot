package com.wick.boot.module.system.app.service;

import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.vo.QueryRolePageReqVO;
import com.wick.boot.common.core.result.PageResult;

/**
 * 角色管理-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
public interface ISystemRoleService {

    /**
     * 角色分页
     *
     * @param reqVO 请求参数
     * @return PageResult<SystemRoleDTO>
     */
    PageResult<SystemRoleDTO> getRolePage(QueryRolePageReqVO reqVO);

}
