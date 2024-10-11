package com.wick.boot.module.tool.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成预览
 *
 * @author Wickson
 * @date 2024-08-07
 */
@ApiModel(description = "代码生成预览")
@Data
public class ToolCodeGenPreviewDTO {

    @ApiModelProperty(value = "文件路径", example = "java/com/wick/boot/module/tools/controller")
    private String path;

    @ApiModelProperty(value = "文件名称", example = "SysTestDemoController.java")
    private String fileName;

    @ApiModelProperty(value = "包路径", example = "com/wick/boot/module/tool")
    private String packagePath;

    @ApiModelProperty(value = "代码内容", example = "Hello World")
    private String content;

}
