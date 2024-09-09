package com.wick.boot.module.system.model.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(value = "SystemDeptAddVO", description = "新增部门信息参数")
public class SystemDeptAddVO {

    @ApiModelProperty(value = "上级部门", required = true, example = "0")
    @NotNull(message = "上级部门不能为空")
    private Long parentId;

    @ApiModelProperty(value = "部门名称", required = true, example = "研发部门")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty(value = "部门编号", required = true, example = "RD001")
    @NotBlank(message = "部门编号不能为空")
    private String code;

    @ApiModelProperty(value = "显示排序", required = true, example = "1")
    @NotNull(message = "显示排序不能为空")
    @Max(value = 99, message = "显示排序不能大于99")
    private Integer sort;

    @ApiModelProperty(value = "部门状态", required = true, example = "1")
    @NotNull(message = "部门状态不能为空")
    private Integer status;
}
