package cn.wickson.security.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 系统管理 - 用户登录条件参数
 *
 * @author ZhangZiHeng
 * @date 2024-04-06
 */
@Setter
@Getter
@ApiModel(value = "QueryDeptListReqVO", description = "用户登录查询条件")
public class AuthUserLoginReqVO {

    @ApiModelProperty(name = "登录账号", required = true, example = "admin")
    @NotBlank(message = "登录账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @ApiModelProperty(name = "登录密码", example = "123456")
    @NotBlank(message = "登录密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @ApiModelProperty(name = "验证码key", example = "c2e71a2de35748fca269164a7d9837b6")
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;

    @ApiModelProperty(name = "验证码", example = "0sov")
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;

}
