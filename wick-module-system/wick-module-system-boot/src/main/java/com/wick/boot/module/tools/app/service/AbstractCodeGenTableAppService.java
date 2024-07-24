package com.wick.boot.module.tools.app.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.tools.mapper.ICodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.CodeGenTable;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代码生成器-防腐层
 *
 * @author ZhangZiHeng
 * @date 2024-07-24
 */
public abstract class AbstractCodeGenTableAppService {

    @Resource
    private ICodeGenTableMapper codeGenTableMapper;

    /**
     * 验证表名
     *
     * @param codeGenTables 代码生成器集合表
     * @param tableNames    表名集合
     */
    protected void validateAddParams(List<CodeGenTableDTO> codeGenTables, List<String> tableNames) {
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
    private void validateDataSource(List<String> tableNames, List<CodeGenTableDTO> codeGenTables) {
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
    private void validateTableNameExisting(List<String> tableNames, List<CodeGenTableDTO> codeGenTables) {
        // 计算 tableNames 中哪些表名在数据源中不存在
        Set<String> existingTableNames = codeGenTables.stream()
                .map(CodeGenTableDTO::getTableName)
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
        List<CodeGenTable> existingCodeGenTables = this.codeGenTableMapper.selectTables(tableNames);

        // 如果 code_gen_table 表中不存在这些表名，则返回
        if (CollUtil.isEmpty(existingCodeGenTables)) {
            return;
        }

        // 获取已经存在的表名集合
        Set<String> existingTableNames = existingCodeGenTables.stream()
                .map(CodeGenTable::getTableName)
                .collect(Collectors.toSet());

        // 计算 tableNames 中哪些表名已经存在
        Collection<String> duplicateTableNames = CollectionUtil.intersection(tableNames, existingTableNames);
        if (CollUtil.isNotEmpty(duplicateTableNames)) {
            String errorMsg = "表" + duplicateTableNames + "已经存在";
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID.getCode(), errorMsg);
        }
    }

}
