package cn.wickson.security.system.security.model;

import cn.wickson.security.system.model.dto.AuthUserInfoDTO;
import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * 系统用户
 */
@Data
public class SystemUserDetails implements UserDetails {

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

    public SystemUserDetails(AuthUserInfoDTO userDetailsDTO) {
        this.userId = userDetailsDTO.getUserId();
        this.deptId = userDetailsDTO.getDeptId();
        this.username = userDetailsDTO.getUsername();
        this.password = userDetailsDTO.getPassword();
        this.enabled = Objects.equals(userDetailsDTO.getStatus(), 1);
        this.authorities = Sets.newHashSet();
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
