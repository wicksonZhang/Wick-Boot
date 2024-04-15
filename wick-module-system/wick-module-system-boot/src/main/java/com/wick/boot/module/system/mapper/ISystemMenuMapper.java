package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.module.system.model.dto.SystemMenuDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.QueryMenuListReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface ISystemMenuMapper extends BaseMapper<SystemMenu> {

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

}
