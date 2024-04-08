package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.wickson.security.commons.constant.GlobalSystemConstants;
import cn.wickson.security.commons.enums.CommonStatusEnum;
import cn.wickson.security.system.app.service.ISystemMenuService;
import cn.wickson.security.system.convert.SystemMenuConvert;
import cn.wickson.security.system.mapper.ISystemMenuMapper;
import cn.wickson.security.system.model.dto.SystemMenuDTO;
import cn.wickson.security.system.model.dto.SystemRouteDTO;
import cn.wickson.security.system.model.entity.SystemMenu;
import cn.wickson.security.system.model.vo.QueryMenuListReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台管理 - 菜单信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<ISystemMenuMapper, SystemMenu> implements ISystemMenuService {

    @Resource
    private ISystemMenuMapper systemMenuMapper;

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
        Long rootNodeId = GlobalSystemConstants.ROOT_NODE_ID;

        // Step-1: 构建菜单树并将部门存入Map
        for (SystemMenu menu : menuList) {
            SystemMenuDTO menuDTO = SystemMenuConvert.INSTANCE.entityToDTOWithChildren(menu);
            menuMap.put(menuDTO.getId(), menuDTO);
        }

        // 将子菜单添加到父部门的children属性中
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
        return menuMap.values().stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
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

    /**
     * 构建路由树
     *
     * @param menuDTOs
     * @return
     */
    private List<SystemRouteDTO> buildRouteTree(List<SystemMenuDTO> menuDTOs) {
        // Step-1: 类型转换
        Map<Long, SystemMenuDTO> menuMap = menuDTOs.stream().collect(Collectors.toMap(SystemMenuDTO::getId, dto -> dto));

        // Step-2: 构建菜单树
        Map<Long, SystemRouteDTO> resultMap = new HashMap<>();
        menuDTOs.stream()
                .filter(menu -> !Objects.equals(GlobalSystemConstants.ROOT_NODE_ID, menu.getParentId()))
                .forEach(menu -> {
                    SystemMenuDTO menuParent = menuMap.get(menu.getParentId());
                    if (menuParent != null) {
                        resultMap.computeIfAbsent(menuParent.getId(), parentId -> {
                            SystemRouteDTO parentRoute = getRoute(menuParent);
                            resultMap.put(parentId, parentRoute);
                            return parentRoute;
                        });
                        // 设置 children 属性
                        SystemRouteDTO parentRoute = resultMap.get(menuParent.getId());
                        SystemRouteDTO childrenRoute = getRoute(menu);
                        parentRoute.getChildren().add(childrenRoute);
                    }
                });

        /* Step-3: 返回结果集 */
        return new ArrayList<>(resultMap.values());
    }

    private SystemRouteDTO getRoute(SystemMenuDTO menu) {
        SystemRouteDTO routeVO = new SystemRouteDTO();
        // 设置 SystemRouteDTO 属性
        routeVO.setName(menu.getPath()); // 根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setRedirect(menu.getRedirect());
        routeVO.setComponent(menu.getComponent());

        // 设置 SystemRouteDTO.Meta 属性
        SystemRouteDTO.Meta meta = new SystemRouteDTO.Meta();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setRoles(menu.getRoles());
        meta.setHidden(CommonStatusEnum.DISABLE.getValue().equals(menu.getVisible()));
        meta.setKeepAlive(true);
        routeVO.setMeta(meta);

        return routeVO;
    }

}
