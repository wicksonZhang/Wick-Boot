package com.wick.boot.module.system.model.vo.role;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.enums.DataSourceEnum;
import com.wick.boot.common.core.validator.InEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 角色信息添加参数VO
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
@Setter
@Getter
@ApiModel(value = "AddRoleVo", description = "角色信息添加条件参数")
public class AddRoleVo {

    @ApiModelProperty(value = "角色名称", required = true, example = "系统管理员")
    @NotBlank(message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "角色编码", required = true, example = "ADMIN")
    @NotBlank(message = "角色编码不能为空")
    private String code;

    @ApiModelProperty(value = "数据权限", required = true, example = "0")
    @InEnum(value = DataSourceEnum.class, message = "数据权限字段必须是 {value}")
    private Integer dataScope;

    @ApiModelProperty(value = "状态(0: 停用、1：正常)", required = true, example = "0")
    @InEnum(value = CommonStatusEnum.class, message = "显示状态必须是 {value}")
    private Integer status;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

}
