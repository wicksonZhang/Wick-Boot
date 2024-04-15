package com.wick.boot.module.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.QueryUserPageReqVO;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 后台管理 - 部门Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Mapper
public interface ISystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 校验用户名唯一
     *
     * @param username 用户名
     * @return AdminUser
     */
    default SystemUser selectByUsername(String username) {
        return selectOne(new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getUsername, username));
    }

    /**
     * 分页查询用户信息
     *
     * @param reqVO 用户请求信息
     * @return PageResult<SystemUser>
     */
    Page<SystemUserDTO> selectPage(Page<SystemUserDTO> page, @Param("reqVO") QueryUserPageReqVO reqVO);

    /**
     * 获取用户认证信息
     *
     * @param username 用户名称
     * @return LoginUserInfoDTO
     */
    LoginUserInfoDTO selectAuthUserInfo(String username);
}
