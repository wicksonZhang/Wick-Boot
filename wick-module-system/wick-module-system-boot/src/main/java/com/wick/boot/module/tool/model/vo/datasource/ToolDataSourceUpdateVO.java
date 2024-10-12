package com.wick.boot.module.tool.model.vo.datasource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 数据源配置更新参数
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Setter
@Getter
@ApiModel(value = "ToolDataSourceUpdateVO", description = "数据源配置更新参数")
public class ToolDataSourceUpdateVO extends ToolDataSourceAddVO {

    @ApiModelProperty(value = "主键ID", required = true, example = "1")
    @NotNull(message = "主键ID不能为空")
    private Long id;
}
