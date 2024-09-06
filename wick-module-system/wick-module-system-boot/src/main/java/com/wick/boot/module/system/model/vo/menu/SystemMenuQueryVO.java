package com.wick.boot.module.system.model.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台管理 - 菜单请求参数
 *
 * @author Wickson
 * @date 2024-04-07
 */
@Setter
@Getter
@ApiModel(value = "SystemMenuQueryVO", description = "菜单信息查询条件参数")
public class SystemMenuQueryVO {

    @ApiModelProperty(value = "菜单名称", example = "系统管理")
    private String name;

    @ApiModelProperty(value = "状态(1->显示；0->隐藏)", example = "1")
    private Integer status;

}
