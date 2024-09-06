package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.model.vo.role.SystemRoleAddVO;
import com.wick.boot.module.system.model.vo.role.SystemRoleUpdateVO;
import com.wick.boot.module.system.service.SystemRoleAbstractService;
import com.wick.boot.module.system.service.SystemRoleMenuService;
import com.wick.boot.module.system.service.SystemRoleService;
import com.wick.boot.module.system.convert.SystemRoleConvert;
import com.wick.boot.module.system.model.dto.role.SystemRoleDTO;
import com.wick.boot.module.system.model.dto.role.SystemRoleOptionsDTO;
import com.wick.boot.module.system.model.entity.SystemRole;
import com.wick.boot.module.system.model.entity.SystemRoleMenu;
import com.wick.boot.module.system.model.vo.role.SystemRoleQueryVO;
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
public class SystemRoleServiceImpl extends SystemRoleAbstractService implements SystemRoleService {

    @Resource
    private SystemRoleMenuService roleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addSystemRole(SystemRoleAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增角色信息 */
        SystemRole systemRole = SystemRoleConvert.INSTANCE.addVoToEntity(reqVO);
        roleMapper.insert(systemRole);
        return systemRole.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemRole(SystemRoleUpdateVO reqVO) {
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
    public void deleteSystemRole(List<Long> ids) {
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
    public SystemRoleDTO getSystemRole(Long id) {
        /* Step-1: 通过ID获取字典类型数据 */
        SystemRole systemRole = this.roleMapper.selectById(id);
        return SystemRoleConvert.INSTANCE.entityToDTO(systemRole);
    }

    @Override
    public PageResult<SystemRoleDTO> getSystemRolePage(SystemRoleQueryVO reqVO) {
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
    public List<SystemRoleOptionsDTO> listRoleOptions() {
        /* Step-1: 查询所有角色信息 */
        List<SystemRole> roles =  this.roleMapper.selectRoleOption();

        /* Step-2: 返回结果集 */
        return SystemRoleConvert.INSTANCE.entityToOptions(roles);
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
