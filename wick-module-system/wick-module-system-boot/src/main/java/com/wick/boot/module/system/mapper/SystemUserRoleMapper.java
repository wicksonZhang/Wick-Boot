package com.wick.boot.module.system.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * 用户-角色 Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
@Mapper
public interface SystemUserRoleMapper extends BaseMapperX<SystemUserRole> {

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

    /**
     * 通过用户Id查询用户信息
     *
     * @param userId 用户id
     * @return
     */
    default List<SystemUserRole> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapper<SystemUserRole>()
                .eq(SystemUserRole::getUserId, userId)
        );
    }

    /**
     * 删除用户-角色表信息
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     */
    default void deleteBatchByUserIdAndRoleIds(Long userId, Set<Long> roleIds) {
        this.delete(new LambdaQueryWrapper<SystemUserRole>()
                .eq(SystemUserRole::getUserId, userId)
                .in(SystemUserRole::getRoleId, roleIds)
        );
    }

    /**
     * 删除用户集合信息
     *
     * @param userIds 用户集合Ids
     */
    default void deleteBatchByUserIds(List<Long> userIds) {
        this.delete(new LambdaQueryWrapper<SystemUserRole>().in(SystemUserRole::getUserId, userIds));
    }
}
