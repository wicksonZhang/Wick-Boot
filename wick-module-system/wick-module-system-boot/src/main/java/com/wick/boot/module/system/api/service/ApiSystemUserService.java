package com.wick.boot.module.system.api.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.module.system.api.ApiSystemUser;
import com.wick.boot.module.system.mapper.SystemUserMapper;
import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import com.wick.boot.module.system.model.entity.SystemUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author Wickson
 * @date 2024-04-13
 */
@Service
public class ApiSystemUserService implements ApiSystemUser {


    @Resource
    private SystemUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginUserInfoDTO getUserByName(String username) {
        /* Step-1: 通过用户名获取用户信息： userId, username, nickname, password, status, dept_id , code */
        LoginUserInfoDTO userInfoDTO = userMapper.selectAuthUserInfo(username);
        if (ObjUtil.isNull(userInfoDTO)) {
            return null;
        }
        return userInfoDTO;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void updateUserLogin(Long userId, String clientIP) {
        this.userMapper.updateById(new SystemUser().setId(userId).setLoginIp(clientIP).setLoginDate(LocalDateTime.now()));
    }
}
