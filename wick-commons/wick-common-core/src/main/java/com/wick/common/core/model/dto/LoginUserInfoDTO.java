package com.wick.common.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 用户认证信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserInfoDTO {

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
    private Integer status;

    /**
     * 角色
     */
    private Set<String> roles;

    /**
     * 权限
     */
    private Set<String> perms;

}
