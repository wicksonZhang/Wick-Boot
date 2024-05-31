package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 后台管理 - 角色Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Mapper
public interface ISystemRoleMapper extends BaseMapperX<SystemRole> {

    /**
     * 获取角色分页信息
     *
     * @param page 分页page
     * @param name 角色名称
     * @param code 角色编码
     * @return Page<SystemRole>
     */
    default Page<SystemRole> selectRolePage(Page<SystemRole> page, String name, String code) {
        return this.selectPage(page, new LambdaQueryWrapper<SystemRole>()
                .likeRight(ObjUtil.isNotNull(name), SystemRole::getName, name)
                .likeRight(ObjUtil.isNotNull(code), SystemRole::getCode, code)
                .ne(SystemRole::getCode, GlobalConstants.ROOT_ROLE_CODE));
    }

    /**
     * 通过角色名称获取角色信息
     *
     * @param name 角色名称
     * @return 角色信息
     */
    default SystemRole selectRoleByName(String name) {
        return selectOne(new LambdaQueryWrapper<SystemRole>().eq(SystemRole::getName, name));
    }

    /**
     * 通过角色编码获取角色信息
     *
     * @param code 角色编码
     * @return 角色信息
     */
    default SystemRole selectRoleByCode(String code) {
        return selectOne(new LambdaQueryWrapper<SystemRole>().eq(SystemRole::getCode, code));
    }

    /**
     * 查询角色信息
     *
     * @return 角色信息集合
     */
    default List<SystemRole> selectRoleOption() {
        return selectList(
                new LambdaQueryWrapper<SystemRole>()
                        .select(SystemRole::getId, SystemRole::getName)
                        .ne(SystemRole::getCode, GlobalConstants.ROOT_ROLE_CODE)
                        .orderByAsc(SystemRole::getSort)
        );
    }

    /**
     * 获取角色的菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单集合
     */
    List<Long> selectRoleMenuIds(Long roleId);

}
