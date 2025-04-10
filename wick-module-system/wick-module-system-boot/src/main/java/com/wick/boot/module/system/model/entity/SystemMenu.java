package com.wick.boot.module.system.model.entity;

import com.wick.boot.module.system.enums.MenuTypeEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 系统管理 - 菜单信息
 *
 * @author Wickson
 * @date 2024-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("system_menu")
public class SystemMenu extends BaseDO {

    @TableId
    @ApiModelProperty(value = "菜单主键ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "父级菜单ID", example = "0")
    private Long parentId;

    @ApiModelProperty(value = "菜单类型(1-菜单；2-目录；3-外链；4-按钮)", example = "1")
    private MenuTypeEnum type;

    @ApiModelProperty(value = "菜单名称", example = "系统管理")
    private String name;

    @ApiModelProperty(value = "路由名称", example = "")
    private String routeName;

    @ApiModelProperty(value = "路由路径", example = "/system")
    private String routePath;

    @ApiModelProperty(value = "组件路径", example = "Layout")
    private String component;

    @ApiModelProperty(value = "权限标识", example = "")
    private String perm;

    @ApiModelProperty(value = "菜单图标", example = "system")
    private String icon;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "显示状态(0->禁用、1->开启)", example = "1")
    private Integer visible;

    @ApiModelProperty(value = "跳转路径", example = "/system/user")
    private String redirect;

    @ApiModelProperty(value = "树的深度", example = "0,1")
    private String treePath;

    @ApiModelProperty(value = "目录", example = "1")
    private Integer alwaysShow;

    @ApiModelProperty(value = "菜单", example = "1")
    private Integer keepAlive;

}
