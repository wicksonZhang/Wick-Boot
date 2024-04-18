package com.wick.boot.module.system.model.vo.menu;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.validator.InEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(value = "AddMenuReqVO", description = "菜单信息添加条件参数")
public class AddMenuReqVO {

    @ApiModelProperty(value = "父级菜单", required = true, example = "1")
    @NotNull(message = "父级菜单不能为空")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称", required = true, example = "用户管理")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单类型
     * {@link com.wick.boot.module.system.enums.MenuTypeEnum }
     */
    @ApiModelProperty(value = "菜单类型", required = true, example = "MENU")
    @NotBlank(message = "菜单类型不能为空")
    private String type;

    @ApiModelProperty(value = "路由路径", required = true, example = "user")
    @NotBlank(message = "路由路径不能为空")
    private String path;

    @ApiModelProperty(value = "显示状态(1:显示，0：隐藏)", required = true, example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "显示状态必须是 {value}")
    private Integer visible;

    @ApiModelProperty(value = "根目录始终显示(1:是，0：否)", example = "0")
    @InEnum(value = CommonStatusEnum.class, message = "根目录始终显示字段必须是 {value}")
    private Integer alwaysShow;

    @ApiModelProperty(value = "是否缓存(1:是，0：否)", example = "0")
    @InEnum(value = CommonStatusEnum.class, message = "是否缓存字段必须是 {value}")
    private Integer keepAlive;

    @ApiModelProperty(value = "页面路径", example = "system/user/index")
    private String component;

    @ApiModelProperty(value = "排序", example = "1")
    @Max(value = 99, message = "排序不能大于 99")
    private Integer sort;

    @ApiModelProperty(value = "图标", example = "tree")
    private String icon;

    @ApiModelProperty(value = "权限标识", example = "sys:user:add")
    private String perm;

}
