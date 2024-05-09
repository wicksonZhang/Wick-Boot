package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.app.service.AbstractSystemRoleAppService;
import com.wick.boot.module.system.app.service.ISystemRoleMenuService;
import com.wick.boot.module.system.app.service.ISystemRoleService;
import com.wick.boot.module.system.convert.SystemRoleConvert;
import com.wick.boot.module.system.model.dto.SystemRoleDTO;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.entity.SystemRoleMenu;
import com.wick.boot.module.system.model.vo.role.AddRoleVo;
import com.wick.boot.module.system.model.vo.role.QueryRolePageReqVO;
import com.wick.boot.module.system.model.vo.role.UpdateRoleVo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Service
public class SystemRoleServiceImpl extends AbstractSystemRoleAppService implements ISystemRoleService {

    @Resource
    private ISystemRoleMenuService roleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addRole(AddRoleVo reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增角色信息 */
        SystemRole systemRole = SystemRoleConvert.INSTANCE.addVoToEntity(reqVO);
        roleMapper.insert(systemRole);
        return systemRole.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(UpdateRoleVo reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新角色信息 */
        SystemRole systemRole = SystemRoleConvert.INSTANCE.updateVoToEntity(reqVO);
        this.roleMapper.updateById(systemRole);

        /* Step-3: 刷新 Role_Menu 权限信息 */
        this.roleMenuService.refreshRolePermsCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(List<Long> ids) {
        /* Step-1: 校验删除角色参数 */
        List<SystemRole> systemRoleList = this.roleMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemRoleList, ids);

        /* Step-2: 删除角色信息 */
        this.roleMapper.deleteBatchIds(systemRoleList);

        /* Step-3: 删除、刷新 Role_Menu 权限信息 */
        // 删除角色-权限菜单信息
        this.roleMenuService.deleteRolePermsByRoleId(ids);
        // 刷新角色-权限菜单缓存
        Set<String> codes = systemRoleList.stream().map(SystemRole::getCode).collect(Collectors.toSet());
        this.roleMenuService.refreshRolePermsCache(codes);
    }

    @Override
    public PageResult<SystemRoleDTO> getRolePage(QueryRolePageReqVO reqVO) {
        Page<SystemRole> pageResult = roleMapper.selectRolePage(
                new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()),
                reqVO.getName(), reqVO.getCode()
        );

        if (CollUtil.isEmpty(pageResult.getRecords())) {
            return PageResult.empty();
        }

        List<SystemRoleDTO> roleDTOList = SystemRoleConvert.INSTANCE.entityToDTOS(pageResult.getRecords());
        return new PageResult<>(roleDTOList, pageResult.getTotal());
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        return roleMapper.selectRoleMenuIds(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "MENU", key = "'ROUTES'") // @CacheEvict 注解的方法在被调用时，会从缓存中移除已存储的数据
    public void assignMenusToRole(Long roleId, List<Long> menuIds) {
        /* Step-1: 校验分配菜单(包括按钮权限)给角色 */
        SystemRole systemRole = this.roleMapper.selectById(roleId);
        this.validateAssignParams(systemRole);

        /* Step-2: 删除角色对应的菜单信息 */
        roleMenuMapper.deleteRolePermsByRoleIds(Collections.singletonList(roleId));

        /* Step-3: 新增角色菜单信息 */
        List<SystemRoleMenu> menuList =
                menuIds.stream()
                        .map(roleMenu -> new SystemRoleMenu(roleId, roleMenu))
                        .collect(Collectors.toList());
        roleMenuMapper.insertBatch(menuList);

        /* Step-4: 刷新缓存权限 */
        this.roleMenuService.refreshRolePermsCache(Collections.singleton(systemRole.getCode()));
    }
}
