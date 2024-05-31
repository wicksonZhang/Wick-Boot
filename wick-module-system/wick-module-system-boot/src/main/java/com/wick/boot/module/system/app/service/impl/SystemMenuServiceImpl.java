package com.wick.boot.module.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.module.system.app.service.AbstractSystemMenuAppService;
import com.wick.boot.module.system.app.service.ISystemMenuService;
import com.wick.boot.module.system.app.service.ISystemRoleMenuService;
import com.wick.boot.module.system.convert.SystemDeptConvert;
import com.wick.boot.module.system.convert.SystemMenuConvert;
import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.wick.boot.module.system.mapper.ISystemMenuMapper;
import com.wick.boot.module.system.model.dto.*;
import com.wick.boot.module.system.model.entity.SystemDept;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.AddMenuReqVO;
import com.wick.boot.module.system.model.vo.menu.QueryMenuListReqVO;
import com.wick.boot.module.system.model.vo.menu.UpdateMenuReqVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单管理-服务实现层
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Service
public class SystemMenuServiceImpl extends AbstractSystemMenuAppService implements ISystemMenuService {

    @Resource
    private ISystemMenuMapper systemMenuMapper;

    @Resource
    private ISystemRoleMenuService roleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = "MENU", key = "'ROUTES'") // @CacheEvict 注解的方法在被调用时，会从缓存中移除已存储的数据
    public Long addMenu(AddMenuReqVO reqVO) {
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
    @CacheEvict(cacheNames = "MENU", key = "'ROUTES'") // @CacheEvict 注解的方法在被调用时，会从缓存中移除已存储的数据
    public void updateMenu(UpdateMenuReqVO reqVO) {
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
    @CacheEvict(cacheNames = "MENU", key = "'ROUTES'") // @CacheEvict 注解的方法在被调用时，会从缓存中移除已存储的数据
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
    public SystemMenu getMenuById(Long id) {
        /* Step-1: 通过菜单ID获取菜单信息 */
        SystemMenu systemMenu = systemMenuMapper.selectById(id);
        if (ObjUtil.isNull(systemMenu)) {
            return SystemMenu.builder().build();
        }

        /* Step-2: 返回结果集 */
        return systemMenu;
    }

    @Override
    public List<SystemMenuDTO> listMenus(QueryMenuListReqVO queryParams) {
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
    @Cacheable(cacheNames = "MENU", key = "'ROUTES'") // 可以将方法运行的结果进行缓存， 下次执行先走缓存， 在走数据库
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
    public List<SystemMenuOptionsDTO> options() {
        /* Step-1: 获取菜单信息 */
        List<SystemMenu> menuList = systemMenuMapper.selectMenuOptions();
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
        Map<Long, SystemRouteDTO> resultMap = Maps.newLinkedHashMap();
        Long rootNodeId = GlobalConstants.ROOT_NODE_ID;

        // Step-1: 构建路由树并将菜单存入Map
        Map<Long, SystemMenuDTO> menuMap = menuDTOs.stream().collect(Collectors.toMap(SystemMenuDTO::getId, dto -> dto));

        // 将子菜单添加到父菜单的children属性中
        for (SystemMenuDTO menuDTO : menuMap.values()) {
            Long parentId = menuDTO.getParentId();
            if (Objects.equals(rootNodeId, parentId) || !menuMap.containsKey(parentId)) {
                continue;
            }
            // 获取或创建父菜单对应的路由
            SystemMenuDTO parentDTO = menuMap.get(parentId);
            SystemRouteDTO systemRouteDTO = resultMap.computeIfAbsent(parentDTO.getId(), route -> getRoute(parentDTO));
            // 设置 children 属性
            systemRouteDTO.getChildren().add(getRoute(menuDTO));
        }

        /* Step-2: 返回结果集 */
        return new ArrayList<>(resultMap.values());
    }

    private SystemRouteDTO getRoute(SystemMenuDTO menuDTO) {
        SystemRouteDTO routeVO = new SystemRouteDTO();

        // 设置 SystemRouteDTO 属性
        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(menuDTO.getPath(), '-'));  // 路由 name 需要驼峰，首字母大写
        routeVO.setName(routeName); // 根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setPath(menuDTO.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setRedirect(menuDTO.getRedirect());
        routeVO.setComponent(menuDTO.getComponent());

        // 设置 SystemRouteDTO.Meta 属性
        SystemRouteDTO.Meta meta = new SystemRouteDTO.Meta();
        meta.setTitle(menuDTO.getName());
        meta.setIcon(menuDTO.getIcon());
        meta.setRoles(menuDTO.getRoles());
        meta.setHidden(CommonStatusEnum.DISABLE.getValue().equals(menuDTO.getVisible()));

        // 【菜单】是否开启页面缓存
        if (MenuTypeEnum.MENU.equals(menuDTO.getType()) && ObjectUtil.equals(menuDTO.getKeepAlive(), 1)) {
            meta.setKeepAlive(true);
        }
        // 【目录】只有一个子路由是否始终显示
        if (MenuTypeEnum.CATALOG.equals(menuDTO.getType()) && ObjectUtil.equals(menuDTO.getAlwaysShow(), 1)) {
            meta.setAlwaysShow(true);
        }
        routeVO.setMeta(meta);
        return routeVO;
    }

}
