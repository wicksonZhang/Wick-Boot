package com.wick.boot.module.system.model.vo.dept;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel(value = "SystemDeptUpdateVO", description = "更新部门信息参数")
public class SystemDeptUpdateVO extends SystemDeptAddVO {

    /**
     * 部门主键ID
     */
    @ApiModelProperty(value = "部门主键ID", required = true, example = "1")
    @NotNull(message = "部门主键ID不能为空")
    private Long id;

}
