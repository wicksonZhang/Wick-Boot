package com.wick.boot.module.tools.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.system.enums.ErrorCodeSystem;
import com.wick.boot.module.tools.mapper.ToolCodeGenTableColumnMapper;
import com.wick.boot.module.tools.mapper.ToolCodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tools.model.vo.column.ToolCodeGenTableColumnAddVO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableAddVO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableUpdateVO;

import javax.annotation.Resource;
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

    @Resource
    private ToolCodeGenTableMapper codeGenTableMapper;

    @Resource
    private ToolCodeGenTableColumnMapper codeGenTableColumnMapper;

    /**
     * 验证表名
     *
     * @param codeGenTables 代码生成器集合表
     * @param tableNames    表名集合
     */
    protected void validateAddParams(List<ToolCodeGenTableDTO> codeGenTables, List<String> tableNames) {
        // 校验表名是否存在于数据源中
        validateDataSource(tableNames, codeGenTables);

        // 校验表名是否存在于数据源中
        validateTableNameExisting(tableNames, codeGenTables);

        // 验证 tableNames 在 code_gen_table 表中已经存在
        validateTableNameByCodeGenTable(tableNames);
    }

    /**
     * 校验表名是否存在于数据源中
     *
     * @param tableNames    表名集合
     * @param codeGenTables 代码生成器集合
     * @throws ServiceException 如果表名在数据源中不存在，抛出异常
     */
    private void validateDataSource(List<String> tableNames, List<ToolCodeGenTableDTO> codeGenTables) {
        // 如果数据源中不存在表
        if (CollUtil.isEmpty(codeGenTables)) {
            String errorMsg = "表" + tableNames + "在数据源中不存在";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

    /**
     * 校验表名是否存在于数据源中
     *
     * @param tableNames    表名集合
     * @param codeGenTables 代码生成器集合
     * @throws ServiceException 如果表名在数据源中不存在，抛出异常
     */
    private void validateTableNameExisting(List<String> tableNames, List<ToolCodeGenTableDTO> codeGenTables) {
        // 计算 tableNames 中哪些表名在数据源中不存在
        Set<String> existingTableNames = codeGenTables.stream()
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
    private void validateTableNameByCodeGenTable(List<String> tableNames) {
        // 查询 code_gen_table 表中是否存在指定的表名
        List<ToolCodeGenTable> existingToolCodeGenTables = this.codeGenTableMapper.selectTables(tableNames);

        // 如果 code_gen_table 表中不存在这些表名，则返回
        if (CollUtil.isEmpty(existingToolCodeGenTables)) {
            return;
        }

        // 获取已经存在的表名集合
        Set<String> existingTableNames = existingToolCodeGenTables.stream()
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
     * @param updateVO 更新参数
     */
    protected void validateUpdateParams(ToolCodeGenTableUpdateVO updateVO) {
        // 校验数据表是否存在
        ToolCodeGenTableAddVO table = updateVO.getTable();
        this.validateCodeGenTable(table.getId());
        // 校验数据表字段是否存在
        List<ToolCodeGenTableColumnAddVO> targetColumns = updateVO.getColumns();
        List<ToolCodeGenTableColumn> sourceColumns = this.validateCodeGenTableColumnByTableId(table.getId());
        // 校验数据表字段是否匹配
        this.validateCodeGenTableColumnByName(sourceColumns, targetColumns);
    }

    /**
     * 校验数据表是否存在
     *
     * @param codeGenTable 数据表Id
     */
    private void validateCodeGenTable(Long codeGenTable) {
        if (ObjUtil.isNull(codeGenTable)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
    }

    /**
     * 校验数据表字段是否存在
     *
     * @param tableId 数据表Id
     * @return 数据表字段集合
     */
    private List<ToolCodeGenTableColumn> validateCodeGenTableColumnByTableId(Long tableId) {
        List<ToolCodeGenTableColumn> tableColumns = this.codeGenTableColumnMapper.selectListByTableId(tableId);
        if (CollUtil.isEmpty(tableColumns)) {
            throw ServiceException.getInstance(ErrorCodeSystem.TOOL_CODE_GEN_TABLE_NOT_EXIST);
        }
        return tableColumns;
    }

    /**
     * 校验数据表字段是否匹配
     *
     * @param sourceColumns 源数据表字段
     * @param targetColumns 目标数据表字段
     */
    private void validateCodeGenTableColumnByName(List<ToolCodeGenTableColumn> sourceColumns, List<ToolCodeGenTableColumnAddVO> targetColumns) {
        Set<String> targetName = sourceColumns.stream()
                .map(ToolCodeGenTableColumn::getColumnName)
                .collect(Collectors.toSet());
        Set<String> sourceName = targetColumns.stream()
                .map(ToolCodeGenTableColumnAddVO::getColumnName)
                .collect(Collectors.toSet());
        Collection<String> columnNames = CollectionUtil.subtract(targetName, sourceName);
        if (CollUtil.isNotEmpty(columnNames)) {
            String errorMsg = "请确认字段" + columnNames + "是否存在";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

    /**
     * 校验预览代码参数
     *
     * @param tableId 数据表Id
     */
    protected void validatePreviewParams(Long tableId) {
        // 校验数据表是否存在
        this.validateCodeGenTable(tableId);
        // 校验数据表字段是否存在
        this.validateCodeGenTableColumnByTableId(tableId);
    }
}
