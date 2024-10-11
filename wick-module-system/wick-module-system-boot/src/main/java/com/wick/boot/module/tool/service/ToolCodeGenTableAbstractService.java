package com.wick.boot.module.tool.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tool.model.vo.column.ToolCodeGenTableColumnAddVO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableAddVO;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代码生成器-防腐层
 *
 * @author Wickson
 * @date 2024-07-24
 */
public abstract class ToolCodeGenTableAbstractService {

    /**
     * 验证表名
     *
     * @param tableNames    表名集合
     * @param codeGenTables 代码生成器集合表
     */
    protected void validateAddParams(List<String> tableNames, List<ToolCodeGenTableDTO> dataSourcesTables, List<ToolCodeGenTable> codeGenTables) {
        // 校验表名是否存在于数据源中
        validateDataSourcesTables(dataSourcesTables);

        // 校验表名是否存在于数据源中
        validateTableNameExisting(tableNames, dataSourcesTables);

        // 验证 tableNames 在 code_gen_table 表中已经存在
        validateTableNameByCodeGenTable(tableNames, codeGenTables);
    }

    /**
     * 校验表名是否存在于数据源中
     *
     * @param codeGenTables 代码生成器集合
     * @throws ServiceException 如果表名在数据源中不存在，抛出异常
     */
    private void validateDataSourcesTables(List<ToolCodeGenTableDTO> codeGenTables) {
        // 如果数据源中不存在表
        if (CollUtil.isEmpty(codeGenTables)) {
            String errorMsg = "请确认导入表是否在数据源中";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

    /**
     * 校验表名是否存在于数据源中
     *
     * @param tableNames        表名集合
     * @param dataSourcesTables 代码生成器集合
     * @throws ServiceException 如果表名在数据源中不存在，抛出异常
     */
    private void validateTableNameExisting(List<String> tableNames, List<ToolCodeGenTableDTO> dataSourcesTables) {
        // 计算 tableNames 中哪些表名在数据源中不存在
        Set<String> existingTableNames = dataSourcesTables.stream()
                .map(ToolCodeGenTableDTO::getTableName)
                .collect(Collectors.toSet());

        Collection<String> nonExistentTableNames = CollectionUtil.subtract(tableNames, existingTableNames);

        // 如果所有表名都存在于数据源中，则返回
        if (CollUtil.isNotEmpty(nonExistentTableNames)) {
            String errorMsg = "表" + nonExistentTableNames + "在数据源中不存在";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

    /**
     * 检查表名是否已经存在于 code_gen_table 表中
     *
     * @param tableNames 表名集合
     * @throws ServiceException 如果表名已经存在，抛出异常
     */
    private void validateTableNameByCodeGenTable(List<String> tableNames, List<ToolCodeGenTable> codeGenTables) {
        // 如果 code_gen_table 表中不存在这些表名，则返回
        if (CollUtil.isEmpty(codeGenTables)) {
            return;
        }

        // 获取已经存在的表名集合
        Set<String> existingTableNames = codeGenTables.stream()
                .map(ToolCodeGenTable::getTableName)
                .collect(Collectors.toSet());

        // 计算 tableNames 中哪些表名已经存在
        Collection<String> duplicateTableNames = CollectionUtil.intersection(tableNames, existingTableNames);
        if (CollUtil.isNotEmpty(duplicateTableNames)) {
            String errorMsg = "表" + duplicateTableNames + "已存在";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

    /**
     * 校验更新参数信息
     *
     * @param table         数据表
     * @param columns       数据表字段
     * @param sourceColumns 源数据表字段
     */
    protected void validateUpdateParams(ToolCodeGenTableAddVO table, List<ToolCodeGenTableColumnAddVO> columns, List<ToolCodeGenTableColumn> sourceColumns) {
        // 校验数据表是否存在
        this.validateCodeGenTableAddVO(table);
        // 校验数据表字段是否存在
        this.validateCodeGenTableColumnAddVO(columns);
        // 校验数据表字段是否匹配
        this.validateCodeGenTableColumnByName(sourceColumns, columns);
    }

    private void validateCodeGenTableAddVO(ToolCodeGenTableAddVO table) {
        if (ObjUtil.isNull(table)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
    }

    private void validateCodeGenTableColumnAddVO(List<ToolCodeGenTableColumnAddVO> columns) {
        if (CollUtil.isEmpty(columns)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
    }

    /**
     * 校验数据表字段是否匹配
     *
     * @param sourceColumns 源数据表字段
     * @param targetColumns 目标数据表字段
     */
    private void validateCodeGenTableColumnByName(List<ToolCodeGenTableColumn> sourceColumns, List<ToolCodeGenTableColumnAddVO> targetColumns) {
        // 提取源数据表字段
        Set<String> sourceName = sourceColumns.stream()
                .map(ToolCodeGenTableColumn::getColumnName)
                .collect(Collectors.toSet());
        // 提取目标数据表字段
        Set<String> targetName = targetColumns.stream()
                .map(ToolCodeGenTableColumnAddVO::getColumnName)
                .collect(Collectors.toSet());
        // 判断是否存在差集
        Collection<String> columnNames = CollectionUtil.subtract(sourceName, targetName);
        if (CollUtil.isNotEmpty(columnNames)) {
            String errorMsg = "请确认字段" + columnNames + "是否存在";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

    /**
     * 校验数据表是否存在
     *
     * @param codeGenTable 数据表
     */
    protected void validateCodeGenTable(ToolCodeGenTable codeGenTable) {
        if (ObjUtil.isNull(codeGenTable)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
    }

    /**
     * 校验数据表字段是否存在
     *
     * @param tableColumns 数据表字段集合
     */
    protected void validateCodeGenTableColumn(List<ToolCodeGenTableColumn> tableColumns) {
        if (CollUtil.isEmpty(tableColumns)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
    }

    /**
     * 删除参数校验
     *
     * @param ids 主键集合
     */
    protected void validateDeleteParams(List<ToolCodeGenTable> toolCodeGenTableList, List<Long> ids) {
        // 验证数据表是否存在
        this.validateToolCodeGenTables(toolCodeGenTableList);
        // 验证数据表集合和 ids 是否匹配
        this.validateToolCodeGenTableByIds(toolCodeGenTableList, ids);
    }

    private void validateToolCodeGenTables(List<ToolCodeGenTable> toolCodeGenTableList) {
        // 校验数据表集合是否存在
        if (CollUtil.isEmpty(toolCodeGenTableList)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
    }

    private void validateToolCodeGenTableByIds(List<ToolCodeGenTable> toolCodeGenTableList, List<Long> ids) {
        // 校验不存在的数据表ID
        List<Long> tableIds = toolCodeGenTableList.stream().map(ToolCodeGenTable::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, tableIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            String errorMsg = "请确认数据表主键 " + errorIds + " 是否存在";
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST.getCode(), errorMsg);
        }
    }
}
