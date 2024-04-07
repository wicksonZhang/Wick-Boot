package cn.wickson.security.system.app.service;

import cn.wickson.security.system.model.dto.SystemMenuDTO;
import cn.wickson.security.system.model.dto.SystemRouteDTO;
import cn.wickson.security.system.model.entity.SystemMenu;
import cn.wickson.security.system.model.vo.QueryMenuListReqVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 后台管理 - 菜单管理
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
public interface ISystemMenuService extends IService<SystemMenu> {

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
