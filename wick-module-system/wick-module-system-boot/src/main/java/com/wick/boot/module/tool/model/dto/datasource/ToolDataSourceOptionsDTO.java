package com.wick.boot.module.tool.model.dto.datasource;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhangZiHeng
 * @date 2024-10-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ToolDataSourceOptionsDTO {

    @ApiModelProperty(value = "菜单ID", example = "1")
    private Long value;

    @ApiModelProperty(value = "菜单名称", example = "Nexus")
    private String label;

}
