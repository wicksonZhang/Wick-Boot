package com.wick.module.system.api;


import com.wick.module.system.model.dto.LoginUserInfoDTO;

/**
 * @author ZhangZiHeng
 * @date 2024-04-13
 */
public interface ApiSystemUser {

    /**
     * 通过用户名获取对象
     *
     * @param username 用户名
     * @return LoginUserInfoDTO
     */
    LoginUserInfoDTO getUserByName(String username);

}
