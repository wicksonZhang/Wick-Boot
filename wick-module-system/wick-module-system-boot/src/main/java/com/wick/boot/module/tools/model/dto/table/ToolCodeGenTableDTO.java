package com.wick.boot.module.tools.model.dto.table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代码自动生成器 - DTO
 *
 * @author Wickson
 * @date 2024-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolCodeGenTableDTO {

    // ========================== 基本信息 ==========================
    @ApiModelProperty(value = "主键id", example = "1")
    private Long id;

    @ApiModelProperty(value = "表名", example = "system_user")
    private String tableName;

    @ApiModelProperty(value = "表描述", example = "用户信息表")
    private String tableComment;

    @ApiModelProperty(value = "类名", example = "ClassName")
    private String className;

    @ApiModelProperty(value = "作者", example = "wickson")
    private String functionAuthor;

    @ApiModelProperty(value = "备注", example = "备注")
    private String remark;

    // ========================== 生成信息 ==========================

    @ApiModelProperty(value = "使用的模板（crud单表操作 tree树表操作 sub主子表操作）", example = "crud")
    private String tplCategory;

    @ApiModelProperty(value = "前端类型（Vue2 Element UI、Vue3 Element Plus）", example = "Vue3 Element Plus")
    private String tplWebType;

    @ApiModelProperty(value = "包路径", example = "com.wick.boot.module.system")
    private String packageName;

    @ApiModelProperty(value = "模块名", example = "system")
    private String moduleName;

    @ApiModelProperty(value = "业务名", example = "user")
    private String businessName;

    @ApiModelProperty(value = "功能名", example = "用户信息")
    private String functionName;

    @ApiModelProperty(value = "生成代码方式", example = "0-zip压缩包 1-自定义路径")
    private String genType;

    @ApiModelProperty(value = "上级菜单", example = "1024")
    private String parentMenuId;

    @ApiModelProperty(value = "自定义路径 ", example = "/")
    private String genPath;

}
