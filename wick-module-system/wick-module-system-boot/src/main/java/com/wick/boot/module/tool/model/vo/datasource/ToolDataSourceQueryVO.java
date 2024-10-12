package com.wick.boot.module.tool.model.vo.datasource;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据源配置分页查询参数
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Setter
@Getter
@ApiModel(value = "ToolDataSourceQueryVO", description = "数据源配置分页查询参数")
public class ToolDataSourceQueryVO extends CommonPageParamVO {

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "用户名")
    private String username;

}
