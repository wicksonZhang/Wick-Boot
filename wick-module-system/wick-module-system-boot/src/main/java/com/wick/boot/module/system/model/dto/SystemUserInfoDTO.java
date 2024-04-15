package com.wick.boot.module.system.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 后台管理 - 当前登录用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "系统登录用户信息")
public class SystemUserInfoDTO {

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    @ApiModelProperty(value = "用户昵称", example = "系统管理员")
    private String nickname;

    @ApiModelProperty(value = "头像地址", example = "https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif")
    private String avatar;

    @ApiModelProperty(value = "用户角色编码集合", example = "ADMIN")
    private Set<String> roles;

    @ApiModelProperty(value = "用户权限标识集合", example = "sys:user:edit")
    private Set<String> perms;

}
