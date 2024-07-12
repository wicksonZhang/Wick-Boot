package com.wick.boot.module.tools.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 更新数据源配置VO
 *
 * @author ZhangZiHeng
 * @date 2024-07-12
 */
@Setter
@Getter
@ApiModel(value = "UpdateDataSourceConfigVO", description = "更新数据源配置信息参数")
public class UpdateDataSourceConfigVO extends AddDataSourceConfigVO {

    @ApiModelProperty(value = "数据源ID", required = true, example = "1")
    @NotNull(message = "数据源主键ID不能为空")
    private Long id;

}
