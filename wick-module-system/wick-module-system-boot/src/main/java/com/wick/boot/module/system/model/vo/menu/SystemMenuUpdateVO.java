package com.wick.boot.module.system.model.vo.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 更新菜单参数VO
 *
 * @author Wickson
 * @date 2024-04-29
 */
@Setter
@Getter
@ApiModel(value = "SystemMenuUpdateVO", description = "更新菜单信息参数")
public class SystemMenuUpdateVO extends SystemMenuAddVO {

    @ApiModelProperty(value = "菜单ID", required = true, example = "1")
    @NotNull(message = "菜单ID不能为空")
    private Long id;

}
