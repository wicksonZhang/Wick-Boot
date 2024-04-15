package com.wick.boot.module.system.api;


import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;

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

    /**
     * 判断密码是否匹配
     *
     * @param rawPassword 未加密的密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
