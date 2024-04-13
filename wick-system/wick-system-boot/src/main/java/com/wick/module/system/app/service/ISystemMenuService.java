package com.wick.module.system.app.service;

import com.wick.module.system.model.dto.SystemMenuDTO;
import com.wick.module.system.model.dto.SystemRouteDTO;
import com.wick.module.system.model.vo.QueryMenuListReqVO;

import java.util.List;

/**
 * 菜单管理-服务层
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
public interface ISystemMenuService {

    /**
     * 获取菜单列表
     *
     * @param queryParams 菜单列表请求参数信息
     * @return
     */
    List<SystemMenuDTO> listMenus(QueryMenuListReqVO queryParams);

    /**
     * 获取路由列表
     *
     * @return
     */
    List<SystemRouteDTO> listRoutes();
}
