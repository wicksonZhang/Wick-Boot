package com.wick.boot.module.tools.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 数据源配置-新增参数
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
@Setter
@Getter
@ApiModel(value = "AddDataSourceConfigVO", description = "新增数据源配置信息参数")
public class AddDataSourceConfigVO {

    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称", required = true, example = "master")
    @NotBlank(message = "数据源名称不能为空")
    private String name;

    /**
     * 数据源连接
     */
    @ApiModelProperty(value = "数据源链接", required = true, example = "jdbc:mysql://127.0.0.1:3306/wick_boot?useUnicode=true&characterEncoding=UTF-8&useSSL=false")
    @NotBlank(message = "数据源链接不能为空")
    private String url;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true, example = "root")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true, example = "root")
    @NotBlank(message = "密码不能为空")
    private String password;

}
