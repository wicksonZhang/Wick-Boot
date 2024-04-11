package cn.wickson.security.system.security.service;

import cn.wickson.security.system.app.service.ISystemUserService;
import cn.wickson.security.system.model.dto.AuthUserInfoDTO;
import cn.wickson.security.system.security.model.SystemUserDetails;
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
    private ISystemUserService systemUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserInfoDTO authUserInfoDTO = systemUserService.getUserByName(username);
        if (authUserInfoDTO == null) {
            // 抛出 UsernameNotFoundException 时， 会被 BadCredentialsException 捕获
            throw new UsernameNotFoundException(username);
        }
        return new SystemUserDetails(authUserInfoDTO);
    }

}
