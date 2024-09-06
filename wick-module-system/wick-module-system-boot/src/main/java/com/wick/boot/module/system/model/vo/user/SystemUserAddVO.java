package com.wick.boot.module.system.model.vo.user;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.validator.InEnum;
import com.wick.boot.common.core.validator.Mobile;
import com.wick.boot.module.system.enums.GenderTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 新增用户信息VO
 *
 * @author Wickson
 * @date 2024-05-11
 */
@Setter
@Getter
@ApiModel(value = "SystemUserAddVO", description = "用户信息添加条件参数")
public class SystemUserAddVO {

    @ApiModelProperty(value = "用户名", required = true, example = "wick")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true, example = "tom")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "所属部门", required = true, example = "1")
    @NotNull(message = "所属部门不能为空")
    private Long deptId;

    @ApiModelProperty(value = "性别", example = "1")
    @InEnum(value = GenderTypeEnum.class, message = "性别状态必须是 {value}")
    private Integer gender;

    @ApiModelProperty(value = "手机号码", example = "13912345689")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "邮箱", example = "123456@qq.com")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "状态", required = true, example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "用户状态必须是 {value}")
    private Integer status;

    @ApiModelProperty(value = "角色", required = true, example = "[1,2]")
    @NotEmpty(message = "用户所属角色不能为空")
    private Set<Long> roleIds;

}
