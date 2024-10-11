package com.wick.boot.module.tool.model.dto.column;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 表结构信息DTO
 *
 * @author Wickson
 * @date 2024-08-01
 */
@ApiModel(description = "表结构信息DTO")
@Data
public class ToolCodeGenColumnDTO {

    @ApiModelProperty(value = "编号", example = "1024")
    private Long id;

    @ApiModelProperty(value = "归属表编号", position = 2, example = "1")
    private Long tableId;

    @ApiModelProperty(value = "列名称", position = 3, example = "username")
    private String columnName;

    @ApiModelProperty(value = "列描述", position = 4, example = "用户名")
    private String columnComment;

    @ApiModelProperty(value = "列类型", position = 5, example = "varchar(64)")
    private String columnType;

    @ApiModelProperty(value = "JAVA类型", position = 6, example = "varchar(64)")
    private String javaType;

    @ApiModelProperty(value = "JAVA字段名", position = 7, example = "username")
    private String javaField;

    @ApiModelProperty(value = "是否主键（1是）", position = 8, example = "0")
    private Integer pk;

    @ApiModelProperty(value = "是否自增（1是）", position = 9, example = "0")
    private Integer increment;

    @ApiModelProperty(value = "是否必填（1是）", position = 10, example = "1")
    private String required;

    @ApiModelProperty(value = "是否为插入字段（1是）", position = 11, example = "1")
    private String created;

    @ApiModelProperty(value = "是否编辑字段（1是）", position = 12, example = "1")
    private String edit;

    @ApiModelProperty(value = "是否列表字段（1是）", position = 13, example = "1")
    private String list;

    @ApiModelProperty(value = "是否查询字段（1是）", position = 14, example = "1")
    private String query;

    @ApiModelProperty(value = "查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）", position = 15, example = "EQ")
    private String queryType;

    @ApiModelProperty(value = "显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）", position = 16, example = "input")
    private String htmlType;

    @ApiModelProperty(value = "字典类型", position = 17)
    private String dictType;

    @ApiModelProperty(value = "排序", position = 18,  example = "3")
    private Integer sort;

}
