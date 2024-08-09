package com.wick.boot.module.tools.model.entity;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 代码自动生成器-数据表字段
 *
 * @author ZhangZiHeng
 * @date 2024-07-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
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
    private String dictType;

    /**
     * 排序
     */
    private Integer sort;


    public boolean isPrimaryKey() {
        return isPrimaryKey(this.pk);
    }

    public boolean isPrimaryKey(String pk) {
        return pk != null && ObjUtil.equals("1", pk);
    }

}
