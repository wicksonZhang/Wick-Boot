package com.wick.boot.module.tool.model.dto.datasource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

/**
 * 数据源配置管理-DTO
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Getter
@Setter
@ApiModel(value = "ToolDataSourceADTO", description = "数据源配置视图DTO")
public class ToolDataSourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    private Long id;

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "数据源连接")
    private String url;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

}


