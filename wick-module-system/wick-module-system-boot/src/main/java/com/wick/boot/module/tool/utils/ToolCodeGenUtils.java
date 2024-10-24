package com.wick.boot.module.tool.utils;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.tool.config.ToolCodeGenConfig;
import com.wick.boot.module.tool.constant.ToolCodeGenConstants;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 处理表数据和表字段工具类
 *
 * @author Wickson
 * @date 2024-08-08
 */
public class ToolCodeGenUtils {

    /**
     * 初始化数据表字段
     */
    public static void initTableField(ToolCodeGenTable codeGenTable, ToolCodeGenConfig toolCodeGenConfig) {
        codeGenTable.setClassName(convertClassName(codeGenTable.getTableName()));
        codeGenTable.setPackageName(toolCodeGenConfig.getPackageName());
        codeGenTable.setModuleName(convertModuleName(toolCodeGenConfig.getPackageName()));
        codeGenTable.setBusinessName(convertBusinessName(codeGenTable.getTableName()));
        codeGenTable.setFunctionName(convertFunctionName(codeGenTable.getTableComment()));
        codeGenTable.setFunctionAuthor(toolCodeGenConfig.getAuthor());
        codeGenTable.setParentMenuId(ToolCodeGenConstants.PARENT_MENU_ID);
    }

    /**
     * Convert to tableName
     *
     * @param tableName 数据表名
     * @return 数据表名
     */
    private static String convertClassName(String tableName) {
        return NamingCase.toPascalCase(tableName);
    }

    /**
     * Convert to ModuleName
     *
     * @param packageName 包名
     * @return 模块名
     */
    private static String convertModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StrUtil.sub(packageName, lastIndex + 1, nameLength);
    }

    /**
     * Convert to BusinessName
     *
     * @param tableName 表名
     * @return 业务名称
     */
    private static String convertBusinessName(String tableName) {
        String business = StrUtil.removePrefix(tableName, tableName.split("_")[0] + "_");
        return StrUtil.replace(business, "_", "-");
    }

    /**
     * Convert to FunctionName
     *
     * @param tableComment 表描述
     * @return 功能名称
     */
    private static String convertFunctionName(String tableComment) {
        return RegExUtils.replaceAll(tableComment, "(?:表)", "");
    }

    /**
     * 初始化数据表字段
     *
     * @param column 数据字段信息
     */
    public static void initColumnField(ToolCodeGenTableColumn column) {
        String dataType = column.getColumnType();
        String columnName = column.getColumnName();
        // 设置java字段名
        if (columnName.startsWith("is_")) {
            String newColumnName = columnName.replace("is_", "");
            column.setJavaField(StrUtil.toCamelCase(newColumnName));
        } else {
            column.setJavaField(StrUtil.toCamelCase(columnName));
        }
        // 设置默认类型
        column.setJavaType(ToolCodeGenConstants.TYPE_STRING);
        column.setQueryType(ToolCodeGenConstants.QUERY_EQ);

        if (ArrayUtil.contains(ToolCodeGenConstants.COLUMNTYPE_STR, dataType)
                || ArrayUtil.contains(ToolCodeGenConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度类型为 "tinytext", "text", "mediumtext", "longtext" 设置为文本域
            String htmlType = ArrayUtil.contains(ToolCodeGenConstants.COLUMNTYPE_TEXT, dataType) ? ToolCodeGenConstants.HTML_TEXTAREA : ToolCodeGenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (ArrayUtil.contains(ToolCodeGenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(ToolCodeGenConstants.TYPE_DATE);
            column.setHtmlType(ToolCodeGenConstants.HTML_DATETIME);
        } else if (ArrayUtil.contains(ToolCodeGenConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(ToolCodeGenConstants.HTML_INPUT);

            // 如果是浮点型 统一用BigDecimal
            if (ToolCodeGenConstants.TYPE_BIGDECIMAL.toLowerCase().equals(dataType)) {
                column.setJavaType(ToolCodeGenConstants.TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (ToolCodeGenConstants.TYPE_INTEGER.toLowerCase().equals(dataType)) {
                column.setJavaType(ToolCodeGenConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setJavaType(ToolCodeGenConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setCreated(ToolCodeGenConstants.REQUIRE);

        // 编辑字段, 列表字段
        if (!ArrayUtil.contains(ToolCodeGenConstants.COLUMNNAME_NOT_EDIT_LIST, columnName)) {
            column.setEdit(ToolCodeGenConstants.REQUIRE);
            column.setList(ToolCodeGenConstants.REQUIRE);
        }
        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(ToolCodeGenConstants.QUERY_LIKE);
            column.setQuery(ToolCodeGenConstants.REQUIRE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(ToolCodeGenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(ToolCodeGenConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(ToolCodeGenConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(ToolCodeGenConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(ToolCodeGenConstants.HTML_EDITOR);
        }

    }

}