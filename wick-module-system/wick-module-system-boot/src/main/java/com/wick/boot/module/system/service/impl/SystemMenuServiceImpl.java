package com.wick.boot.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.module.system.convert.SystemMenuConvert;
import com.wick.boot.module.system.mapper.SystemMenuMapper;
import com.wick.boot.module.system.model.dto.menu.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.menu.SystemMenuOptionsDTO;
import com.wick.boot.module.system.model.dto.menu.SystemRouteDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.SystemMenuAddVO;
import com.wick.boot.module.system.model.vo.menu.SystemMenuQueryVO;
import com.wick.boot.module.system.model.vo.menu.SystemMenuUpdateVO;
import com.wick.boot.module.system.service.SystemMenuAbstractService;
import com.wick.boot.module.system.service.SystemMenuService;
import com.wick.boot.module.system.service.SystemRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单管理-服务实现层
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Service
public class SystemMenuServiceImpl extends SystemMenuAbstractService implements SystemMenuService {

    @Resource
    private SystemMenuMapper systemMenuMapper;

    @Resource
    private SystemRoleMenuService roleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(SystemMenuAddVO reqVO) {
        /* Step-1: 校验新增菜单参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 新增菜单信息 */
        SystemMenu systemMenu = SystemMenuConvert.INSTANCE.addVoToEntity(reqVO);
        // 获取TreePath
        String treePath = getTreePath(reqVO.getParentId());
        systemMenu.setTreePath(treePath);
        this.systemMenuMapper.insert(systemMenu);
        return systemMenu.getId();
    }

    private String getTreePath(Long parentId) {
        // 如果父级节点是根节点直接返回
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            return GlobalConstants.ROOT_NODE_ID.toString();
        }
        SystemMenu systemMenu = this.systemMenuMapper.selectById(parentId);
        return systemMenu.getTreePath() + "," + parentId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SystemMenuUpdateVO reqVO) {
        /* Step-1: 校验更新菜单参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 更新菜单信息 */
        // Convert VO To Entity
        SystemMenu systemMenu = SystemMenuConvert.INSTANCE.updateVoToEntity(reqVO);
        // get treePath
        String treePath = getTreePath(reqVO.getParentId());
        systemMenu.setTreePath(treePath);
        this.systemMenuMapper.updateById(systemMenu);

        /* Step-3: 刷新角色对应的菜单权限 */
        this.roleMenuService.refreshRolePermsCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(List<Long> ids) {
        /* Step-1: 校验删除菜单参数 */
        List<SystemMenu> systemMenuList = this.systemMenuMapper.selectBatchIds(ids);
        this.validateDeleteParams(systemMenuList, ids);

        /* Step-2: 批量删除数据, 包含该菜单或者子级菜单 */
        // 查询菜单以及子菜单信息
        Set<SystemMenu> removeMenuList = this.systemMenuMapper.selectMenuByIdOrTreePath(ids);
        this.systemMenuMapper.deleteBatchIds(removeMenuList);

        /* Step-3: 刷新角色对应的菜单权限 */
        // 删除对应的 角色-权限 信息表
        this.roleMenuService.deleteRolePermsByMenuId(ids);
        // 刷新对应的 角色-权限
        this.roleMenuService.refreshRolePermsCache();
    }

    @Override
    public SystemMenu getSystemMenu(Long id) {
        /* Step-1: 通过菜单ID获取菜单信息 */
        SystemMenu systemMenu = systemMenuMapper.selectById(id);
        if (ObjUtil.isNull(systemMenu)) {
            return SystemMenu.builder().build();
        }

        /* Step-2: 返回结果集 */
        return systemMenu;
    }

    @Override
    public List<SystemMenuDTO> listMenus(SystemMenuQueryVO queryParams) {
        /* Step-1: 获取菜单信息 */
        List<SystemMenu> menuList = systemMenuMapper.selectList(queryParams);
        if (CollUtil.isEmpty(menuList)) {
            return Lists.newArrayList();
        }

        /* Step-2: 返回结果集 */
        return buildMenuTree(menuList);
    }

    /**
     * 构建菜单树
     *
     * @param menuList 菜单列表信息
     * @return List<SystemMenuDTO>
     */
    private List<SystemMenuDTO> buildMenuTree(List<SystemMenu> menuList) {
        Map<Long, SystemMenuDTO> menuMap = new HashMap<>();
        Long rootNodeId = GlobalConstants.ROOT_NODE_ID;

        // Step-1: 构建菜单树并存入Map
        for (SystemMenu menu : menuList) {
            SystemMenuDTO menuDTO = SystemMenuConvert.INSTANCE.entityToDTOWithChildren(menu);
            menuMap.put(menuDTO.getId(), menuDTO);
        }

        // 将子菜单添加到父级菜单的children属性中
        for (SystemMenuDTO menuDTO : menuMap.values()) {
            if (Objects.equals(rootNodeId, menuDTO.getParentId())) {
                continue;
            }
            SystemMenuDTO parentMenuDTO = menuMap.get(menuDTO.getParentId());
            if (parentMenuDTO != null) {
                parentMenuDTO.getChildren().add(menuDTO);
            }
        }

        /* Step-2: 返回根节点结果集 */
        List<SystemMenuDTO> rootMenus = menuMap.values().stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(rootMenus)) {
            return new ArrayList<>(menuMap.values());
        }
        return rootMenus;
    }

    @Override
    public List<SystemRouteDTO> listRoutes() {
        /* Step-1: 获取菜单信息 */
        List<SystemMenuDTO> routeDTOS = systemMenuMapper.selectListRoutes();
        if (CollUtil.isEmpty(routeDTOS)) {
            return Lists.newArrayList();
        }

        /* Step-2: 构建菜单树 */
        return buildRouteTree(routeDTOS);
    }

    @Override
    public List<SystemMenuOptionsDTO> options(Boolean onlyParent) {
        /* Step-1: 获取菜单信息 */
        List<SystemMenu> menuList = systemMenuMapper.selectMenuOptions(onlyParent);
        if (CollUtil.isEmpty(menuList)) {
            return Lists.newArrayList();
        }

        /* Step-2: 构建菜单树 */
        List<SystemMenuDTO> menuDTOList = buildMenuTree(menuList);

        /* Step-3: 返回结果集 */
        return SystemMenuConvert.INSTANCE.entityToDTOList(menuDTOList);
    }

    /**
     * 构建路由树
     *
     * @param menuDTOs 菜单DTO
     * @return List<SystemRouteDTO>
     */
    private List<SystemRouteDTO> buildRouteTree(List<SystemMenuDTO> menuDTOs) {
        // 创建结果Map，以父菜单ID为键
        Long rootNodeId = GlobalConstants.ROOT_NODE_ID;

        // Step-1: 构建路由树并将菜单存入Map
        Map<Long, SystemMenuDTO> menuMap = menuDTOs.stream().collect(Collectors.toMap(SystemMenuDTO::getId, dto -> dto));

        // 将子菜单添加到父菜单的children属性中
        for (SystemMenuDTO menuDTO : menuMap.values()) {
            Long parentId = menuDTO.getParentId();
            if (Objects.equals(rootNodeId, menuDTO.getParentId())) {
                continue;
            }
            // 获取或创建父菜单对应的路由
            SystemMenuDTO parentMenuDTO = menuMap.get(parentId);
            if (parentMenuDTO != null) {
                parentMenuDTO.getChildren().add(menuDTO);
            }
        }

        /* Step-2: 返回根节点结果集 */
        List<SystemMenuDTO> rootMenus = menuMap.values().stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());

        /* Step-2: 返回结果集 */
        return SystemMenuConvert.INSTANCE.convertToRoute(rootMenus);
    }

}
