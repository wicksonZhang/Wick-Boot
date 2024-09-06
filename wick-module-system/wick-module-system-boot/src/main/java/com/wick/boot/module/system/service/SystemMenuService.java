package com.wick.boot.module.system.service;

import com.wick.boot.module.system.model.dto.menu.SystemMenuDTO;
import com.wick.boot.module.system.model.dto.menu.SystemMenuOptionsDTO;
import com.wick.boot.module.system.model.dto.menu.SystemRouteDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.SystemMenuAddVO;
import com.wick.boot.module.system.model.vo.menu.SystemMenuQueryVO;
import com.wick.boot.module.system.model.vo.menu.SystemMenuUpdateVO;

import java.util.List;

/**
 * 菜单管理-服务层
 *
 * @author Wickson
 * @date 2024-04-07
 */
public interface SystemMenuService {

    /**
     * 新增菜单
     *
     * @param reqVO 新增菜单请求参数
     * @return 菜单主键ID
     */
    Long add(SystemMenuAddVO reqVO);

    /**
     * 更新菜单
     *
     * @param reqVO 更新菜单请求参数
     */
    void update(SystemMenuUpdateVO reqVO);

    /**
     * 删除菜单
     *
     * @param ids 菜单集合
     */
    void deleteMenu(List<Long> ids);

    /**
     * 通过菜单ID获取菜单数据
     *
     * @param id 菜单ID
     * @return
     */
    SystemMenu getSystemMenu(Long id);

    /**
     * 获取菜单列表
     *
     * @param queryParams 菜单列表请求参数信息
     * @return 菜单列表集合
     */
    List<SystemMenuDTO> listMenus(SystemMenuQueryVO queryParams);

    /**
     * 获取路由列表
     *
     * @return 菜单列表集合
     */
    List<SystemRouteDTO> listRoutes();

    /**
     * 菜单选项信息
     *
     * @return 菜单列表集合
     */
    List<SystemMenuOptionsDTO> options(Boolean onlyParent);
}
