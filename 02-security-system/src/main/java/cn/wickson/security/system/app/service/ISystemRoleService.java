package cn.wickson.security.system.app.service;

import cn.wickson.security.commons.result.PageResult;
import cn.wickson.security.system.model.dto.SystemRoleDTO;
import cn.wickson.security.system.model.vo.QueryRolePageReqVO;

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
