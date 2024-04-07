package cn.wickson.security.system.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.wickson.security.commons.constant.SystemConstants;
import cn.wickson.security.commons.enums.UseStatusEnum;
import cn.wickson.security.system.app.service.ISystemMenuService;
import cn.wickson.security.system.convert.SystemMenuConvert;
import cn.wickson.security.system.enums.MenuTypeEnum;
import cn.wickson.security.system.mapper.ISystemMenuMapper;
import cn.wickson.security.system.model.dto.SystemMenuDTO;
import cn.wickson.security.system.model.dto.SystemRouteDTO;
import cn.wickson.security.system.model.entity.SystemMenu;
import cn.wickson.security.system.model.vo.QueryMenuListReqVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

        /* Step-2: entity To DTO */
        List<SystemMenuDTO> menuDTOS = SystemMenuConvert.INSTANCE.entityToDTOS(menuList);

        /* Step-3: 返回结果集 */
        return buildMenuTree(menuDTOS);
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
     * @param routeDTOS
     * @return
     */
    private List<SystemRouteDTO> buildRouteTree(List<SystemMenuDTO> routeDTOS) {
        /* Step-1: 类型转换 */
        Map<Long, SystemMenuDTO> menuMap = routeDTOS.stream().collect(Collectors.toMap(SystemMenuDTO::getId, dto -> dto));

        /* Step-3: 构建菜单树 */
        Long rootNodeId = SystemConstants.ROOT_NODE_ID;
        Map<Long, SystemRouteDTO> resultMap = Maps.newHashMap();
        routeDTOS.stream().filter(menu -> !Objects.equals(rootNodeId, menu.getParentId())).forEach(menu -> {
            SystemMenuDTO menuParent = menuMap.get(menu.getParentId());
            if (menuParent != null) {
                SystemRouteDTO systemRouteDTO = resultMap.get(menuParent.getId());
                if (systemRouteDTO == null) {
                    SystemRouteDTO route = getRoute(menuParent);
                    resultMap.put(menuParent.getId(), route);
                } else {
                    // 设置 children 属性
                    SystemRouteDTO childrenRoute = getRoute(menu);
                    systemRouteDTO.getChildren().add(childrenRoute);
                }
            }
        });
        return new ArrayList<>(resultMap.values());
    }

    private SystemRouteDTO getRoute(SystemMenuDTO menu) {
        SystemRouteDTO routeVO = new SystemRouteDTO();
        // 设置 SystemRouteDTO 属性
        routeVO.setName(menu.getPath()); //  根据name路由跳转 this.$router.push({name:xxx})
        routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
        routeVO.setRedirect(menu.getRedirect());
        routeVO.setComponent(menu.getComponent());

        // 设置 SystemRouteDTO.Meta 属性
        SystemRouteDTO.Meta meta = new SystemRouteDTO.Meta();
        meta.setTitle(menu.getName());
        meta.setIcon(menu.getIcon());
        meta.setRoles(menu.getRoles());
        meta.setHidden(UseStatusEnum.DISABLE.getValue().equals(menu.getVisible()));
        meta.setKeepAlive(true);
        routeVO.setMeta(meta);

        return routeVO;
    }

    /**
     * 构建菜单树
     *
     * @param menuDTOS
     * @return
     */
    private List<SystemMenuDTO> buildMenuTree(List<SystemMenuDTO> menuDTOS) {
        /* Step-1: 类型转换 */
        Map<Long, SystemMenuDTO> menuMap = menuDTOS.stream().collect(Collectors.toMap(SystemMenuDTO::getId, dto -> dto));

        /* Step-2: 构建菜单树 */
        Long rootNodeId = SystemConstants.ROOT_NODE_ID;
        menuDTOS.stream().filter(menu -> !Objects.equals(rootNodeId, menu.getParentId())).forEach(menu -> {
            SystemMenuDTO systemMenuDTO = menuMap.get(menu.getParentId());
            if (systemMenuDTO != null) {
                systemMenuDTO.getChildren().add(menu);
            }
        });

        /* Step-3: 返回结果集 */
        return menuMap.values()
                .stream()
                .filter(deptDTO -> Objects.equals(rootNodeId, deptDTO.getParentId()))
                .collect(Collectors.toList());
    }

}
