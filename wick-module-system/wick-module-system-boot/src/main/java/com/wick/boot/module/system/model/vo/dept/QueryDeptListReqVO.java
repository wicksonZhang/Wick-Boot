package com.wick.boot.module.system.model.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统管理 - 用户信息查询条件参数
 *
 * @author Wickson
 * @date 2024-04-06
 */
@Setter
@Getter
@ApiModel(value = "QueryDeptListReqVO", description = "部门信息查询条件参数")
public class QueryDeptListReqVO {

    @ApiModelProperty(value = "关键字(部门名称)", example = "Nexus")
    private String name;

    @ApiModelProperty(value = "状态(1->正常；0->禁用)", example = "1")
    private Integer status;

}
