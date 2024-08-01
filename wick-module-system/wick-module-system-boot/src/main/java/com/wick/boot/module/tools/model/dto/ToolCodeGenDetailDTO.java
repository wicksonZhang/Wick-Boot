package com.wick.boot.module.tools.model.dto;

import com.wick.boot.module.tools.model.dto.column.ToolCodeGenColumnDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTableDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 代码生成表和字段的明细
 *
 * @author ZhangZiHeng
 * @date 2024-08-01
 */
@Data
public class ToolCodeGenDetailDTO {

    @ApiModelProperty(value = "表定义")
    private ToolCodeGenTableDTO table;

    @ApiModelProperty(value = "字段定义")
    private List<ToolCodeGenColumnDTO> columns;

}
