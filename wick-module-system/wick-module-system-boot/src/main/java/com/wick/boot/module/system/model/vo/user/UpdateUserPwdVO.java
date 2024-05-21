package com.wick.boot.module.system.model.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 更新用户密码VO
 *
 * @author ZhangZiHeng
 * @date 2024-05-21
 */
@Setter
@Getter
@ApiModel(value = "UpdateUserPwdVO", description = "用户密码编辑条件参数")
public class UpdateUserPwdVO {

    @ApiModelProperty(value = "用户Id", required = true, example = "1")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户密码", required = true, example = "123456")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

}
