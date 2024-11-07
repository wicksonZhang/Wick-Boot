package com.wick.boot.module.tool.model.entity;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import com.wick.boot.module.tool.constant.ToolCodeGenConstants;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 代码自动生成器-数据表字段
 *
 * @author Wickson
 * @date 2024-07-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tool_code_gen_table_column")
public class ToolCodeGenTableColumn extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 归属表编号
     */
    private Long tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    @NotBlank(message = "Java属性不能为空")
    private String javaField;

    /**
     * 是否主键（1是）
     */
    @TableField("is_pk")
    private String pk;

    /**
     * 是否自增（1是）
     */
    @TableField("is_increment")
    private String increment;

    /**
     * 是否必填（1是）
     */
    @TableField("is_required")
    private String required;

    /**
     * 是否为插入字段（1是）
     */
    @TableField("is_insert")
    private String created;

    /**
     * 是否编辑字段（1是）
     */
    @TableField("is_edit")
    private String edit;

    /**
     * 是否列表字段（1是）
     */
    @TableField("is_list")
    private String list;

    /**
     * 是否查询字段（1是）
     */
    @TableField("is_query")
    private String query;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;

    /**
     * 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件）
     */
    private String htmlType;

    /**
     * 字典类型
     */
    private String dictCode;

    /**
     * 排序
     */
    private Integer sort;

    public boolean isPage() {
        return this.list != null && StrUtil.equals("1", this.list);
    }

    public boolean isInsert() {
        return this.created != null && StrUtil.equals("1", this.created);
    }

    public boolean isUpdate() {
        return this.edit != null && StrUtil.equals("1", this.edit);
    }

    public boolean isUsableColumn() {
        // isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
        return StrUtil.equalsAnyIgnoreCase(this.javaField, "parentId", "orderNum", "remark");
    }

    public boolean isSuperColumn() {
        return StrUtil.equalsAnyIgnoreCase(this.javaField, ToolCodeGenConstants.BASE_ENTITY);
    }

    public boolean isPrimaryKey() {
        return !(this.pk != null && ObjUtil.equals("1", this.pk));
    }

}
