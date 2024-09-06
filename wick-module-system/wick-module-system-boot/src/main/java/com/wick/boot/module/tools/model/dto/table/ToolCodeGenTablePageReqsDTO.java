package com.wick.boot.module.tools.model.dto.table;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 代码生成器列表返回详细信息
 *
 * @author Wickson
 * @date 2024-08-02
 */
@Data
@ToString(callSuper = true)
public class ToolCodeGenTablePageReqsDTO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "表名称不能为空")
    private String tableName;

    @ApiModelProperty(value = "表描述不能为空")
    private String tableComment;

    @ApiModelProperty(value = "类名", example = "ClassName")
    private String className;

    @ApiModelProperty(value = "创建时间", example = "2024-07-23 02:24:37")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2024-07-23 02:24:37")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
