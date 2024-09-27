package com.wick.boot.module.tools.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.config.ToolCodeGenConfig;
import com.wick.boot.module.tools.convert.ToolCodeGenTableConvert;
import com.wick.boot.module.tools.engine.ToolCodeGenEngine;
import com.wick.boot.module.tools.mapper.ToolCodeGenTableColumnMapper;
import com.wick.boot.module.tools.mapper.ToolCodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tools.model.vo.column.ToolCodeGenTableColumnAddVO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableAddVO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableQueryVO;
import com.wick.boot.module.tools.model.vo.table.ToolCodeGenTableUpdateVO;
import com.wick.boot.module.tools.service.ToolCodeGenTableAbstractService;
import com.wick.boot.module.tools.service.ToolCodeGenTableService;
import com.wick.boot.module.tools.utils.ToolCodeGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码自动生成器-服务实现类
 *
 * @author Wickson
 * @date 2024-07-23
 */
@Slf4j
@Service
public class ToolToolCodeGenTableServiceImpl extends ToolCodeGenTableAbstractService implements ToolCodeGenTableService {

    @Resource
    private ToolCodeGenTableMapper codeGenTableMapper;

    @Resource
    private ToolCodeGenTableColumnMapper codeGenTableColumnMapper;

    @Resource
    private ToolCodeGenConfig toolCodeGenConfig;

    @Resource
    private ToolCodeGenEngine toolCodeGenEngine;

    @Override
    public PageResult<ToolCodeGenTablePageReqsDTO> selectDbTableList(ToolCodeGenTableQueryVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<ToolCodeGenTable> pageResult = this.codeGenTableMapper.selectDataSourcePage(
                new Page<>(queryVO.getPageNumber(), queryVO.getPageSize()), queryVO
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<ToolCodeGenTablePageReqsDTO> codeGenDTOS = ToolCodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(List<String> tableNames) {
        /* Step-1：参数验证 */
        // 根据数据源的数据表，排除 tool_code_、flyway_ 的表信息
        List<ToolCodeGenTableDTO> dataSourcesTables = this.codeGenTableMapper.selectByTableNames(tableNames);
        // 根据数据表查询对应 code_gen_table 表中的数据
        List<ToolCodeGenTable> codeGenTables = this.codeGenTableMapper.selectTables(tableNames);
        this.validateAddParams(tableNames, dataSourcesTables, codeGenTables);

        /* Step-2: 导入数据表 */
        importGenTable(dataSourcesTables);
    }

    /**
     * 导入数据表
     *
     * @param dataSourcesTables 数据表信息
     */
    private void importGenTable(List<ToolCodeGenTableDTO> dataSourcesTables) {
        /* Step-1: convert dto to ToolCodeGenTable */
        List<ToolCodeGenTable> toolCodeGenTables = Lists.newArrayList();
        for (ToolCodeGenTableDTO toolCodeGenTableDTO : dataSourcesTables) {
            ToolCodeGenTable codeGenTable = BeanUtil.copyProperties(toolCodeGenTableDTO, ToolCodeGenTable.class);
            ToolCodeGenUtils.initTableField(codeGenTable, toolCodeGenConfig);
            toolCodeGenTables.add(codeGenTable);
        }
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
                ToolCodeGenUtils.initColumnField(column);
                saveColumns.add(column);
            }
            this.codeGenTableColumnMapper.insertBatch(saveColumns);
        }
    }

    @Override
    public PageResult<ToolCodeGenTablePageReqsDTO> selectCodeGenTableList(ToolCodeGenTableQueryVO queryVO) {
        /* Step-1: 根据数据源获取对应的表信息 */
        Page<ToolCodeGenTable> pageResult = this.codeGenTableMapper.selectCodeGenTablePage(queryVO);

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        /* Step-4: 返回分页结果 */
        List<ToolCodeGenTablePageReqsDTO> codeGenDTOS = ToolCodeGenTableConvert.INSTANCE.entityToCodeGenDTOS(pageResult.getRecords());
        return new PageResult<>(codeGenDTOS, pageResult.getTotal());
    }

    @Override
    public ToolCodeGenDetailDTO getDetails(Long tableId) {
        // 查询 tool_code_gen_table 信息
        ToolCodeGenTable table = this.codeGenTableMapper.selectById(tableId);
        // 查询 tool_code_gen_table_column 信息
        List<ToolCodeGenTableColumn> columns = this.codeGenTableColumnMapper.selectListByTableId(tableId);
        return ToolCodeGenTableConvert.INSTANCE.convertDetailDTO(table, columns);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ToolCodeGenTableUpdateVO updateVO) {
        /* Step-1: 校验更新参数信息 */
        ToolCodeGenTableAddVO table = updateVO.getTable();
        List<ToolCodeGenTableColumnAddVO> columns = updateVO.getColumns();
        List<ToolCodeGenTableColumn> codeGenTableColumns = this.codeGenTableColumnMapper.selectListByTableId(table.getId());
        this.validateUpdateParams(table, columns, codeGenTableColumns);

        /* Step-2: 更新数据 */
        // 更新 ToolCodeGenTable 数据
        ToolCodeGenTable codeGenTable = BeanUtil.copyProperties(table, ToolCodeGenTable.class);
        this.codeGenTableMapper.updateById(codeGenTable);
        // 更新 ToolCodeGEnTableColumn 数据
        List<ToolCodeGenTableColumnAddVO> sourceColumns = updateVO.getColumns();
        List<ToolCodeGenTableColumn> targetColumns = BeanUtil.copyToList(sourceColumns, ToolCodeGenTableColumn.class);
        targetColumns.forEach(column -> this.codeGenTableColumnMapper.updateById(column));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteToolCodeGenTable(List<Long> ids) {
        /* Step-1: 验证删除参数 */
        List<ToolCodeGenTable> toolCodeGenTableList = this.codeGenTableMapper.selectBatchIds(ids);
        this.validateDeleteParams(toolCodeGenTableList, ids);

        /* Step-2: 先删除从表 tool_code_gen_table_column, 在删除主表 tool_code_gen_table */
        // 删除从表 tool_code_gen_table_column
        List<ToolCodeGenTableColumn> columns = getToolCodeGenTableColumnByTableIds(ids);
        if (CollUtil.isNotEmpty(columns)) {
            this.codeGenTableColumnMapper.deleteBatchIds(columns);
        }
        // 删除主表
        this.codeGenTableMapper.deleteBatchIds(ids);
    }

    private List<ToolCodeGenTableColumn> getToolCodeGenTableColumnByTableIds(List<Long> tableIds) {
        return this.codeGenTableColumnMapper.selectList(
                new LambdaQueryWrapper<ToolCodeGenTableColumn>().in(ToolCodeGenTableColumn::getTableId, tableIds));
    }

    @Override
    public List<ToolCodeGenPreviewDTO> previewCode(Long tableId) {
        /* Step-1: 校验参数 */
        // 查询数据表
        ToolCodeGenTable codeGenTable = this.codeGenTableMapper.selectById(tableId);
        this.validateCodeGenTable(codeGenTable);
        // 查询数据表字段
        List<ToolCodeGenTableColumn> columns = this.codeGenTableColumnMapper.selectListByTableId(tableId);
        this.validateCodeGenTableColumn(columns);

        /* Step-2: 查询*/
        List<ToolCodeGenTable> subTables = null;
        List<List<ToolCodeGenTableColumn>> subColumnsList = null;

        /* Step-2: 执行代码生成器模板引擎 */
        return toolCodeGenEngine.execute(codeGenTable, columns, null, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDb(Long tableId) {
        /* Step-1: 校验参数 */
        // 查询数据表
        ToolCodeGenTable codeGenTable = this.codeGenTableMapper.selectById(tableId);
        this.validateCodeGenTable(codeGenTable);
        // 查询数据表字段
        List<ToolCodeGenTableColumn> columns = this.codeGenTableColumnMapper.selectListByTableId(tableId);
        this.validateCodeGenTableColumn(columns);
        // 查询数据表的源信息
        List<ToolCodeGenTableColumn> sourceColumns = this.codeGenTableColumnMapper.selectDbTableColumnsByName(codeGenTable.getTableName());
        this.validateCodeGenTableColumn(sourceColumns);

        /* Step-2: */
    }
}
