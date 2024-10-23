package com.wick.boot.module.tool.model.vo.datasource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 数据源配置新增参数
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Getter
@Setter
@ApiModel(value = "ToolDataSourceAddVO", description = "数据源配置新增参数")
public class ToolDataSourceAddVO {

    @ApiModelProperty(value = "参数名称", required = true)
    @NotNull(message = "参数名称不能为空")
    private String name;

    @ApiModelProperty(value = "数据源连接", required = true)
    @NotNull(message = "数据源连接不能为空")
    private String url;

    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "密码不能为空")
    private String password;

}


