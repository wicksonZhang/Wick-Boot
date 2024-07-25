package com.wick.boot.module.tools.app.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.app.service.AbstractCodeGenTableAppService;
import com.wick.boot.module.tools.app.service.ICodeGenTableService;
import com.wick.boot.module.tools.constant.CodeGenConstants;
import com.wick.boot.module.tools.convert.CodeGenTableConvert;
import com.wick.boot.module.tools.mapper.ICodeGenTableColumnMapper;
import com.wick.boot.module.tools.mapper.ICodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.CodeGenTable;
import com.wick.boot.module.tools.model.entity.CodeGenTableColumn;
import com.wick.boot.module.tools.model.vo.QueryCodeGenTablePageReqVO;
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
public class CodeGenTableTableServiceImpl extends AbstractCodeGenTableAppService implements ICodeGenTableService {

    @Resource
    private ICodeGenTableMapper codeGenTableMapper;

    @Resource
    private ICodeGenTableColumnMapper codeGenTableColumnMapper;

    @Override
    public PageResult<CodeGenTableDTO> selectDbTableList(QueryCodeGenTablePageReqVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<CodeGenTable> pageResult = this.codeGenTableMapper.selectDataSourcePage(
                new Page<>(queryVO.getPageNumber(), queryVO.getPageSize()), queryVO
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<CodeGenTableDTO> codeGenDTOS = CodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(List<String> tableNames) {
        /* Step-1：参数验证 */
        List<CodeGenTableDTO> codeGenTables = this.codeGenTableMapper.selectByTableNames(tableNames);
        this.validateAddParams(codeGenTables, tableNames);

        /* Step-2: 导入数据表 */
        importGenTable(codeGenTables);
    }

    /**
     * 导入数据表
     *
     * @param codeGenTableDTOS 数据表信息
     */
    private void importGenTable(List<CodeGenTableDTO> codeGenTableDTOS) {
        /* Step-1: convert dto to CodeGenTable */
        List<CodeGenTable> codeGenTables = CodeGenTableConvert.INSTANCE.dtoToEntity(codeGenTableDTOS);
        this.codeGenTableMapper.insertBatch(codeGenTables);

        /* Step-2: convert dto to CodeGenTableColumn */
        List<CodeGenTableColumn> saveColumns = new ArrayList<>();
        for (CodeGenTable codeGenTable : codeGenTables) {
            String tableName = codeGenTable.getTableName();
            // 通过表名查询对应字段信息
            List<CodeGenTableColumn> columns = this.codeGenTableColumnMapper.selectDbTableColumnsByName(tableName);
            for (CodeGenTableColumn column : columns) {
                column.setTableId(codeGenTable.getId());
                // 初始化部分字段信息
                initColumnField(column);
                saveColumns.add(column);
            }
        }
        this.codeGenTableColumnMapper.insertBatch(saveColumns);
    }

    private void initColumnField(CodeGenTableColumn column) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        // 设置java字段名
        column.setJavaField(StrUtil.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(CodeGenConstants.TYPE_STRING);
        column.setQueryType(CodeGenConstants.QUERY_EQ);

        if (arraysContains(CodeGenConstants.COLUMNTYPE_STR, dataType) || arraysContains(CodeGenConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(CodeGenConstants.COLUMNTYPE_TEXT, dataType) ? CodeGenConstants.HTML_TEXTAREA : CodeGenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(CodeGenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(CodeGenConstants.TYPE_DATE);
            column.setHtmlType(CodeGenConstants.HTML_DATETIME);
        } else if (arraysContains(CodeGenConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(CodeGenConstants.HTML_INPUT);

            // 如果是浮点型 统一用BigDecimal
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(CodeGenConstants.TYPE_BIGDECIMAL);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setJavaType(CodeGenConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setJavaType(CodeGenConstants.TYPE_LONG);
            }
        }

        // BO对象 默认插入勾选
        if (!arraysContains(CodeGenConstants.COLUMNNAME_NOT_ADD, columnName)) {
            column.setInsert(CodeGenConstants.REQUIRE);
        }
        // BO对象 默认编辑勾选
        if (!arraysContains(CodeGenConstants.COLUMNNAME_NOT_EDIT, columnName)) {
            column.setEdit(CodeGenConstants.REQUIRE);
        }
        // BO对象 默认是否必填勾选
        if (!arraysContains(CodeGenConstants.COLUMNNAME_NOT_EDIT, columnName)) {
            column.setRequired(CodeGenConstants.REQUIRE);
        }
        // VO对象 默认返回勾选
        if (!arraysContains(CodeGenConstants.COLUMNNAME_NOT_LIST, columnName)) {
            column.setList(CodeGenConstants.REQUIRE);
        }
        // BO对象 默认查询勾选
        if (!arraysContains(CodeGenConstants.COLUMNNAME_NOT_QUERY, columnName)) {
            column.setQuery(CodeGenConstants.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(CodeGenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(CodeGenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(CodeGenConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(CodeGenConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(CodeGenConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(CodeGenConstants.HTML_EDITOR);
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
    public PageResult<CodeGenTableDTO> selectCodeGenTableList(QueryCodeGenTablePageReqVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<CodeGenTable> pageResult = this.codeGenTableMapper.selectCodeGenTablePage(queryVO);

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<CodeGenTableDTO> codeGenDTOS = CodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }
}
