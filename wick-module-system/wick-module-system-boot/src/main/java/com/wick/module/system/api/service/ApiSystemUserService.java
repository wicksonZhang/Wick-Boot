package com.wick.module.system.api.service;

import cn.hutool.core.util.ObjUtil;
import com.wick.module.system.api.ApiSystemUser;
import com.wick.module.system.mapper.ISystemUserMapper;
import com.wick.common.core.model.dto.LoginUserInfoDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author ZhangZiHeng
 * @date 2024-04-13
 */
@Service
public class ApiSystemUserService implements ApiSystemUser {

    @Resource
    private ISystemUserMapper userMapper;

    @Override
    public LoginUserInfoDTO getUserByName(String username) {
        /* Step-1: 通过用户名获取用户信息： userId, username, nickname, password, status, dept_id , code */
        LoginUserInfoDTO userInfoDTO = userMapper.selectAuthUserInfo(username);
        if (ObjUtil.isNull(userInfoDTO)) {
            return null;
        }

        /* Step-2: 通过角色信息获取权限信息 */
        Set<String> roles = userInfoDTO.getRoles();
//        userInfoDTO.setPerms(getPerms(roles));
        return userInfoDTO;
    }

}
