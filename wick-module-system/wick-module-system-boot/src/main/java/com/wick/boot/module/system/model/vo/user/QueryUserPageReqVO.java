package com.wick.boot.module.system.model.vo.user;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统管理 - 用户信息查询条件参数
 *
 * @author ZhangZiHeng
 * @date 2024-04-03
 */
@Setter
@Getter
@ApiModel(value = "QueryUserPageReqVO", description = "用户信息查询条件参数")
public class QueryUserPageReqVO extends CommonPageParamVO {

    @ApiModelProperty(value = "用户账号，模糊匹配", example = "admin")
    private String username;

    @ApiModelProperty(value = "手机号码，模糊匹配", example = "13312345678")
    private String mobile;

    @ApiModelProperty(value = "用户状态用户状态(1:正常、0:禁用)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "部门ID", example = "1")
    private Long deptId;

}
