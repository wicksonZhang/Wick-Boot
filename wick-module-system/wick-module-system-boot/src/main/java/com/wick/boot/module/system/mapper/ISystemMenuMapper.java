package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.menu.SystemMenuDTO;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.system.model.vo.menu.QueryMenuListReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据菜单id模糊查询
     *
     * @param ids 菜单集合ids
     * @return 菜单集合数据
     */
    default Set<SystemMenu> selectMenuByIdOrTreePath(List<Long> ids) {
        // 模糊匹配查询：
        // SELECT id FROM system_menu WHERE deleted=0 AND (id = ? OR CONCAT(',', tree_path, ',') LIKE CONCAT('%,', ?, ',%'))
        List<SystemMenu> resultList = Lists.newArrayList();
        ids.forEach(id -> {
            List<SystemMenu> byTreePath = this.selectList(new LambdaQueryWrapper<SystemMenu>()
                    .select(SystemMenu::getId)
                    .eq(SystemMenu::getId, id)
                    .or()
                    .apply("CONCAT(',', tree_path, ',') LIKE CONCAT('%,', {0}, ',%')", id)
            );
            resultList.addAll(byTreePath);
        });
        return Sets.newHashSet(resultList);
    }

    /**
     * 获取系统菜单选项
     *
     * @return 菜单集合
     */
    default List<SystemMenu> selectMenuOptions() {
        return selectList(new LambdaQueryWrapper<SystemMenu>()
                .select(SystemMenu::getId,
                        SystemMenu::getParentId,
                        SystemMenu::getName,
                        SystemMenu::getType)
                .eq(SystemMenu::getVisible, CommonStatusEnum.ENABLE.getValue())
                .orderByAsc(SystemMenu::getSort));
    }
}
