package com.wick.boot.module.tool.model.vo.table;

import com.wick.boot.module.tool.model.vo.column.ToolCodeGenTableColumnAddVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 修改代码生成器字段信息
 *
 * @author Wickson
 * @date 2024-08-06
 */
@Data
@ApiModel(description = "修改代码生成器字段信息")
public class ToolCodeGenTableUpdateVO {

    /**
     * 代码生成表定义创建VO
     */
    @ApiModelProperty(value = "代码生成表定义创建VO", position = 1)
    @NotNull(message = "代码生成表定义创建VO不能为空")
    private ToolCodeGenTableAddVO table;

    /**
     * 代码生成表字段创建VO
     */
    @ApiModelProperty(value = "代码生成表字段创建VO", position = 2)
    @NotEmpty(message = "代码生成表字段创建VO不能为空")
    private List<ToolCodeGenTableColumnAddVO> columns;

}
