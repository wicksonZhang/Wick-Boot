package com.wick.boot.module.system.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户-角色 Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
@Mapper
public interface ISystemUserRoleMapper extends BaseMapperX<SystemUserRole> {

    /**
     * 通过角色Id查询用户信息
     *
     * @param ids 角色Ids
     * @return 用户-角色 集合
     */
    default List<SystemUserRole> selectUserRoleByRoleIds(List<Long> ids) {
        return selectList(new LambdaQueryWrapper<SystemUserRole>()
                .in(CollUtil.isNotEmpty(ids), SystemUserRole::getRoleId, ids)
        );
    }
}
