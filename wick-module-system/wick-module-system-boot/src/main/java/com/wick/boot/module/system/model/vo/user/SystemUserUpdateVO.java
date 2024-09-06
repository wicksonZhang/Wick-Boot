package com.wick.boot.module.system.model.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 用户更新VO
 *
 * @author Wickson
 * @date 2024-05-11
 */
@Setter
@Getter
@ApiModel(value = "UpdateUserVO", description = "用户信息编辑条件参数")
public class SystemUserUpdateVO extends SystemUserAddVO {

    @ApiModelProperty(value = "用户Id", required = true, example = "1")
    @NotNull(message = "用户id不能为空")
    private Long id;

}
