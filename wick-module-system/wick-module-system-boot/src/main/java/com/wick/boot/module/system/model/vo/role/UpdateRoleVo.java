package com.wick.boot.module.system.model.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 角色编辑Vo
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
@Setter
@Getter
@ApiModel(value = "UpdateRoleVo", description = "角色信息编辑条件参数")
public class UpdateRoleVo extends AddRoleVo {

    @ApiModelProperty(value = "角色Id", required = true, example = "1")
    @NotNull(message = "角色id不能为空")
    private Long id;

}
