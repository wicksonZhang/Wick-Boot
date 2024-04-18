package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.SystemMenuDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.QueryMenuListReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单-Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface ISystemMenuMapper extends BaseMapperX<SystemMenu> {

    default List<SystemMenu> selectList(QueryMenuListReqVO queryParams) {
        return selectList(new LambdaQueryWrapper<SystemMenu>()
                .likeRight(ObjUtil.isNotNull(queryParams.getName()), SystemMenu::getName, queryParams.getName())
                .eq(ObjUtil.isNotNull(queryParams.getStatus()), SystemMenu::getVisible, queryParams.getStatus())
                .orderByAsc(SystemMenu::getSort));
    }

    /**
     * 获取路由列表
     *
     * @return List<SystemRouteDTO>
     */
    List<SystemMenuDTO> selectListRoutes();

    /**
     * 通过菜单父级Id和菜单名称获取菜单信息
     *
     * @param parentId 父级ID
     * @param name     菜单名称
     * @return SystemMenu 菜单信息
     */
    default Long selectCountByParentIdAndName(Long parentId, String name) {
        return selectCount(new LambdaQueryWrapper<SystemMenu>()
                .eq(SystemMenu::getParentId, parentId)
                .eq(SystemMenu::getName, name)
        );
    }
}
