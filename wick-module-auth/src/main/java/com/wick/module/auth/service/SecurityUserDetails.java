package com.wick.module.auth.service;

import com.wick.module.auth.userdetails.LoginUserDetails;
import com.wick.module.system.api.ApiSystemUser;
import com.wick.module.system.model.dto.LoginUserInfoDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统用户认证
 */
@Service
public class SecurityUserDetails implements UserDetailsService {

    @Resource
    private ApiSystemUser systemUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserInfoDTO loginUserInfoDTO = systemUser.getUserByName(username);
        if (loginUserInfoDTO == null) {
            // 抛出 UsernameNotFoundException 时， 会被 BadCredentialsException 捕获
            throw new UsernameNotFoundException(username);
        }
        return new LoginUserDetails(loginUserInfoDTO);
    }

}
