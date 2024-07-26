package com.wick.boot.module.tools.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.app.service.AbstractToolCodeGenTableAppService;
import com.wick.boot.module.tools.app.service.IToolCodeGenTableService;
import com.wick.boot.module.tools.constant.ToolCodeGenConstants;
import com.wick.boot.module.tools.convert.ToolCodeGenTableConvert;
import com.wick.boot.module.tools.mapper.IToolCodeGenTableColumnMapper;
import com.wick.boot.module.tools.mapper.IToolCodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.ToolCodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tools.model.vo.QueryToolCodeGenTablePageReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代码自动生成器-服务实现类
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
@Slf4j
@Service
public class ToolToolCodeGenTableServiceImpl extends AbstractToolCodeGenTableAppService implements IToolCodeGenTableService {

    @Resource
    private IToolCodeGenTableMapper codeGenTableMapper;

    @Resource
    private IToolCodeGenTableColumnMapper codeGenTableColumnMapper;

    @Override
    public PageResult<ToolCodeGenTableDTO> selectDbTableList(QueryToolCodeGenTablePageReqVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<ToolCodeGenTable> pageResult = this.codeGenTableMapper.selectDataSourcePage(
                new Page<>(queryVO.getPageNumber(), queryVO.getPageSize()), queryVO
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<ToolCodeGenTableDTO> codeGenDTOS = ToolCodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(List<String> tableNames) {
        /* Step-1：参数验证 */
        List<ToolCodeGenTableDTO> codeGenTables = this.codeGenTableMapper.selectByTableNames(tableNames);
        this.validateAddParams(codeGenTables, tableNames);

        /* Step-2: 导入数据表 */
        importGenTable(codeGenTables);
    }

    /**
     * 导入数据表
     *
     * @param toolCodeGenTableDTOS 数据表信息
     */
    private void importGenTable(List<ToolCodeGenTableDTO> toolCodeGenTableDTOS) {
        /* Step-1: convert dto to ToolCodeGenTable */
        List<ToolCodeGenTable> toolCodeGenTables = ToolCodeGenTableConvert.INSTANCE.dtoToEntity(toolCodeGenTableDTOS);
        this.codeGenTableMapper.insertBatch(toolCodeGenTables);

        /* Step-2: convert dto to ToolCodeGenTableColumn */
        List<ToolCodeGenTableColumn> saveColumns = new ArrayList<>();
        for (ToolCodeGenTable toolCodeGenTable : toolCodeGenTables) {
            String tableName = toolCodeGenTable.getTableName();
            // 通过表名查询对应字段信息
            List<ToolCodeGenTableColumn> columns = this.codeGenTableColumnMapper.selectDbTableColumnsByName(tableName);
            for (ToolCodeGenTableColumn column : columns) {
                column.setTableId(toolCodeGenTable.getId());
                // 初始化部分字段信息
                initColumnField(column);
                saveColumns.add(column);
            }
        }
        this.codeGenTableColumnMapper.insertBatch(saveColumns);
    }

    private void initColumnField(ToolCodeGenTableColumn column) {
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

        // BO对象 默认插入勾选
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_ADD, columnName)) {
            column.setInsert(ToolCodeGenConstants.REQUIRE);
        }
        // BO对象 默认编辑勾选
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_EDIT, columnName)) {
            column.setEdit(ToolCodeGenConstants.REQUIRE);
        }
        // BO对象 默认是否必填勾选
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_EDIT, columnName)) {
            column.setRequired(ToolCodeGenConstants.REQUIRE);
        }
        // VO对象 默认返回勾选
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_LIST, columnName)) {
            column.setList(ToolCodeGenConstants.REQUIRE);
        }
        // BO对象 默认查询勾选
        if (!arraysContains(ToolCodeGenConstants.COLUMNNAME_NOT_QUERY, columnName)) {
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

    @Override
    public PageResult<ToolCodeGenTableDTO> selectCodeGenTableList(QueryToolCodeGenTablePageReqVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<ToolCodeGenTable> pageResult = this.codeGenTableMapper.selectCodeGenTablePage(queryVO);

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<ToolCodeGenTableDTO> codeGenDTOS = ToolCodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }
}
