package com.wick.boot.module.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import com.wick.boot.module.system.model.vo.user.QueryUserPageReqVO;
import com.wick.boot.module.system.model.vo.user.UserExportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台管理 - 部门Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Mapper
public interface ISystemUserMapper extends BaseMapperX<SystemUser> {

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

    /**
     * 查询手机号是否重复
     *
     * @param mobile 手机号
     * @return 手机号数量
     */
    default Long selectCountByMobile(String mobile) {
        return selectCount(new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getMobile, mobile));
    }

    /**
     * 查询邮箱是否重复
     *
     * @param email 邮箱
     * @return 邮箱数量
     */
    default Long selectCountByEmail(String email) {
        return selectCount(new LambdaQueryWrapper<SystemUser>().eq(SystemUser::getEmail, email));
    }

    /**
     * 导出用户
     *
     * @param queryParams 分页查询条件
     * @return List<UserExportVO>
     */
    List<UserExportVO> listExportUsers(@Param("reqVO") QueryUserPageReqVO queryParams);
}
