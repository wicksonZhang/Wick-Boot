package com.wick.boot.module.system.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.constant.GlobalConstants;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;

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

}
