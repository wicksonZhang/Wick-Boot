package cn.wickson.security.system.security.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.wickson.security.commons.constant.GlobalSystemConstants;
import cn.wickson.security.system.security.model.SystemUserDetails;
import com.google.common.collect.Sets;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Set;

/**
 * Security 用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-11
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的 SystemUserDetails 对象，如果未登录则返回 null
     */
    public static SystemUserDetails getUserDetails() {
        // 从 SecurityContextHolder 中获取当前的认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        // 获取认证对象中的主体信息
        Object principal = authentication.getPrincipal();
        if (principal instanceof SystemUserDetails) {
            return (SystemUserDetails) principal;
        }
        return null;
    }

    /**
     * 获取角色信息
     *
     * @return Set<String>
     */
    public static Set<String> getRoles() {
        Set<String> resultSets = Sets.newHashSet();

        /* Step-1: 获取 SystemUserDetails 用户信息 */
        SystemUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return resultSets;
        }

        /* Step-2: 获取 authorities 角色信息 */
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (CollUtil.isEmpty(authorities)) {
            return resultSets;
        }

        /* Step-3: 移除角色的前缀 ROLE_ */
        for (GrantedAuthority item : authorities) {
            String authority = item.getAuthority();
            String role_ = GlobalSystemConstants.ROLE;
            if (authority.startsWith(role_)) {
                String role = StrUtil.removePrefix(authority, role_);
                resultSets.add(role);
            }
        }
        return resultSets;
    }

}
