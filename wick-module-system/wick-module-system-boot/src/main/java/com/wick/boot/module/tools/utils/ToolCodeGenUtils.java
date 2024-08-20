package com.wick.boot.module.tools.utils;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.tools.config.ToolCodeGenConfig;
import com.wick.boot.module.tools.constant.ToolCodeGenConstants;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 处理表数据和表字段工具类
 *
 * @author ZhangZiHeng
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
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + 1, nameLength);
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
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        // 设置java字段名
        column.setJavaField(StrUtil.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(ToolCodeGenConstants.TYPE_STRING);
        column.setQueryType(ToolCodeGenConstants.QUERY_EQ);

        if (arraysContains(ToolCodeGenConstants.COLUMNTYPE_STR, dataType) || arraysContains(ToolCodeGenConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(ToolCodeGenConstants.COLUMNTYPE_TEXT, dataType) ? ToolCodeGenConstants.HTML_TEXTAREA : ToolCodeGenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(ToolCodeGenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(ToolCodeGenConstants.TYPE_DATE);
            column.setHtmlType(ToolCodeGenConstants.HTML_DATETIME);
        } else if (arraysContains(ToolCodeGenConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(ToolCodeGenConstants.HTML_INPUT);

            // 如果是浮点型 统一用BigDecimal
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(ToolCodeGenConstants.TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setJavaType(ToolCodeGenConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setJavaType(ToolCodeGenConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setCreated(ToolCodeGenConstants.REQUIRE);

        // 编辑字段
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPrimaryKey()) {
            column.setEdit(ToolCodeGenConstants.REQUIRE);
        }
        // 列表字段
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPrimaryKey()) {
            column.setList(ToolCodeGenConstants.REQUIRE);
        }
        // 查询字段
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPrimaryKey()) {
            column.setQuery(ToolCodeGenConstants.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(ToolCodeGenConstants.QUERY_LIKE);
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

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }


    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }

}
