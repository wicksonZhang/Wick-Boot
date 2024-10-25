package com.wick.boot.module.tool.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 代码自动生成器表
 *
 * @author Wickson
 * @date 2024-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tool_code_gen_table")
public class ToolCodeGenTable extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据源id
     */
    private Long dataSourceId;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
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
    private String packageName;

    /**
     * 生成模块名
     */
    private String moduleName;

    /**
     * 生成业务名
     */
    private String businessName;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 生成作者
     */
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
     * 父菜单ID
     */
    private Long parentMenuId;

    /**
     * 父菜单名称(排除)
     */
    @TableField(exist = false)
    private String parentMenuName;

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
