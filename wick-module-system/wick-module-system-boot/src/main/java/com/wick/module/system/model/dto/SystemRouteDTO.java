package com.wick.module.system.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理 - 前端路由对象
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "前端路由对象")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SystemRouteDTO {

    @ApiModelProperty(value = "路由路径", example = "user")
    private String path;

    @ApiModelProperty(value = "组件路径", example = "system/user/index")
    private String component;

    @ApiModelProperty(value = "跳转链接", example = "/system/user")
    private String redirect;

    @ApiModelProperty(value = "路由名称")
    private String name;

    @ApiModelProperty(value = "路由属性")
    private Meta meta;

    @Data
    @ApiModel(value = "路由属性类型")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {

        @ApiModelProperty(value = "路由title")
        private String title;

        @ApiModelProperty(value = "ICON")
        private String icon;

        @ApiModelProperty(value = "是否隐藏(true-是 false-否)", example = "true")
        private Boolean hidden;

        @ApiModelProperty(value = "拥有路由权限的角色编码", example = "['ADMIN','ROOT']")
        private List<String> roles;

        @ApiModelProperty(value = "【菜单】是否开启页面缓存", example = "true")
        private Boolean keepAlive;

        @ApiModelProperty(value = "【目录】只有一个子路由是否始终显示", example = "true")
        private Boolean alwaysShow;
    }

    @ApiModelProperty(value = "子路由列表")
    private List<SystemRouteDTO> children = new ArrayList<>();

}
