package com.wick.module.auth.userdetails;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Sets;
import com.wick.common.core.constant.GlobalSystemConstants;
import com.wick.common.core.model.dto.LoginUserInfoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统用户
 */
@Data
@NoArgsConstructor
public class LoginUserDetails implements UserDetails {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态
     */
    private Boolean enabled;

    /**
     * 用户权限列表
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public LoginUserDetails(LoginUserInfoDTO userDetailsDTO) {
        // 用户Id
        this.userId = userDetailsDTO.getUserId();
        // 部门Id
        this.deptId = userDetailsDTO.getDeptId();
        // 用户名称
        this.username = userDetailsDTO.getUsername();
        // 用户密码
        this.password = userDetailsDTO.getPassword();
        // 账号状态
        this.enabled = Objects.equals(userDetailsDTO.getStatus(), 1);
        // 角色Code
        Set<String> roles = userDetailsDTO.getRoles();
        Set<SimpleGrantedAuthority> authorities = Sets.newHashSet();
        if (CollectionUtil.isNotEmpty(roles)) {
            // 每个角色前都添加了 "ROLE_" 前缀，符合 Spring Security 角色的命名规范。
            // 参考：https://juejin.cn/post/6995136682320199710
            authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(GlobalSystemConstants.ROLE + role))
                    .collect(Collectors.toSet());
        }
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 账号是否未过期
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否未被锁定
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户凭证是否未过期
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否启用
     *
     * @return enabled
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
