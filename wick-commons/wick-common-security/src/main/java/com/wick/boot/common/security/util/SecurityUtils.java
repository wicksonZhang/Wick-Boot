package com.wick.boot.common.security.util;

import com.wick.boot.module.system.model.dto.LoginUserInfoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
     * @return 当前登录用户的 LoginUserInfoDTO 对象，如果未登录则返回 null
     */
    public static LoginUserInfoDTO getUserDetails() {
        // 从 SecurityContextHolder 中获取当前的认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        // 获取认证对象中的主体信息
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUserInfoDTO) {
            return (LoginUserInfoDTO) principal;
        }
        return null;
    }

}
