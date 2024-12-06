package com.wick.boot.module.system.model.dto;

import com.wick.boot.common.core.enums.UserTypeEnum;
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
     * 用户类型
     * 关联 {@link UserTypeEnum}
     */
    private Integer userType;

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
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 是否在线
     */
    private Boolean disconnected;

    /**
     * 最后登录时间
     */
    private String loginDate;

    /**
     * 角色
     */
    private Set<String> roles;

    /**
     * 权限
     */
    private Set<String> perms;

}
