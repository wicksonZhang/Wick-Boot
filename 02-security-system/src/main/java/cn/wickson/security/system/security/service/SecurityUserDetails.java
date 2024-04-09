package cn.wickson.security.system.security.service;

import cn.wickson.security.system.app.service.ISystemUserService;
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
        return null;
    }

}
