package com.wick.boot.module.system.service;

import com.wick.boot.module.system.model.dto.role.SystemRoleDTO;
import com.wick.boot.module.system.model.dto.role.SystemRoleOptionsDTO;
import com.wick.boot.module.system.model.vo.role.SystemRoleAddVO;
import com.wick.boot.module.system.model.vo.role.SystemRoleQueryVO;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.vo.role.SystemRoleUpdateVO;

import java.util.List;

/**
 * 角色管理-服务层
 *
 * @author Wickson
 * @date 2024-04-07
 */
public interface SystemRoleService {

    /**
     * 新增角色信息
     *
     * @param reqVO 新增角色请求参数
     * @return 角色主键Id
     */
    Long addSystemRole(SystemRoleAddVO reqVO);

    /**
     * 编辑角色信息
     *
     * @param reqVO 编辑角色请求参数
     */
    void updateSystemRole(SystemRoleUpdateVO reqVO);

    /**
     * 删除角色信息
     *
     * @param ids id集合
     */
    void deleteSystemRole(List<Long> ids);

    /**
     * 获取角色信息ById
     *
     * @param id 角色Id
     * @return
     */
    SystemRoleDTO getSystemRole(Long id);

    /**
     * 角色分页
     *
     * @param reqVO 请求参数
     * @return PageResult<SystemRoleDTO>
     */
    PageResult<SystemRoleDTO> getSystemRolePage(SystemRoleQueryVO reqVO);

    /**
     * 获取角色下拉选项
     *
     * @return List<SystemRoleOptionsDTO>
     */
    List<SystemRoleOptionsDTO> listRoleOptions();

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色id
     * @return 菜单ID集合
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * 分配菜单(包括按钮权限)给角色
     *
     * @param roleId  角色Id
     * @param menuIds 菜单集合
     */
    void assignMenusToRole(Long roleId, List<Long> menuIds);
}
