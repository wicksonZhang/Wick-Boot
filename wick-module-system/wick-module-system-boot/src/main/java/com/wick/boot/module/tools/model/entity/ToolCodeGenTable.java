package com.wick.boot.module.tools.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 代码自动生成器表
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@TableName("tool_code_gen_table")
public class ToolCodeGenTable extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 表名称
     */
    @NotBlank(message = "表名称不能为空")
    private String tableName;

    /**
     * 表描述
     */
    @NotBlank(message = "表描述不能为空")
    private String tableComment;

    /**
     * 关联父表的表名
     */
    private String subTableName;

    /**
     * 本表关联父表的外键名
     */
    private String subTableFkName;

    /**
     * 实体类名称(首字母大写)
     */
    @NotBlank(message = "实体类名称不能为空")
    private String className;

    /**
     * 使用的模板（crud单表操作 tree树表操作 sub主子表操作）
     */
    private String tplCategory;

    /**
     * 前端类型（element-ui模版 element-plus模版）
     */
    private String tplWebType;

    /**
     * 生成包路径
     */
    @NotBlank(message = "生成包路径不能为空")
    private String packageName;

    /**
     * 生成模块名
     */
    @NotBlank(message = "生成模块名不能为空")
    private String moduleName;

    /**
     * 生成业务名
     */
    @NotBlank(message = "生成业务名不能为空")
    private String businessName;

    /**
     * 生成功能名
     */
    @NotBlank(message = "生成功能名不能为空")
    private String functionName;

    /**
     * 生成作者
     */
    @NotBlank(message = "作者不能为空")
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 其它生成选项
     */
    private String options;

    /**
     * 备注
     */
    private String remark;

//    /**
//     * 主键信息
//     */
//    private ToolCodeGenTableColumn pkColumn;
//
//    /**
//     * 子表信息
//     */
//    private ToolCodeGenTable subTable;
//
//    /**
//     * 表列信息
//     */
//    @Valid
//    private List<ToolCodeGenTableColumn> columns;
//
//
//    /**
//     * 树编码字段
//     */
//    private String treeCode;
//
//    /**
//     * 树父编码字段
//     */
//    private String treeParentCode;
//
//    /**
//     * 树名称字段
//     */
//    private String treeName;
//

//
//    /**
//     * 上级菜单名称字段
//     */
//    private String parentMenuName;

}
