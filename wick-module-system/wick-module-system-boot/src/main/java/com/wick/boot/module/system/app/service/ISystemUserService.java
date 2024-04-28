package com.wick.boot.module.system.app.service;


import com.wick.boot.module.system.model.dto.SystemUserInfoDTO;
import com.wick.boot.module.system.model.dto.SystemUserDTO;
import com.wick.boot.module.system.model.vo.QueryUserPageReqVO;
import com.wick.boot.common.core.result.PageResult;

/**
 * 用户管理-服务层
 */
public interface ISystemUserService {

    /**
     * 通过用户名称获取用户信息
     *
     * @param username 用户名
     * @return SystemUserDTO
     */
    SystemUserDTO getUserInfo(String username);

    /**
     * 获取当前用户信息
     *
     * @return SystemUserInfoDTO
     */
    SystemUserInfoDTO getCurrentUserInfo();

    /**
     * 返回用户分页数据信息
     *
     * @param reqVO 用户分页查询请求数据
     * @return PageResult<SystemUserDTO>
     */
    PageResult<SystemUserDTO> getUserPage(QueryUserPageReqVO reqVO);

}
