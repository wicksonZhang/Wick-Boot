package com.wick.boot.module.tools.app.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.app.service.AbstractToolCodeGenTableAppService;
import com.wick.boot.module.tools.app.service.IToolCodeGenTableService;
import com.wick.boot.module.tools.config.ToolCodeGenConfig;
import com.wick.boot.module.tools.convert.ToolCodeGenTableConvert;
import com.wick.boot.module.tools.engine.ToolCodeGenEngine;
import com.wick.boot.module.tools.mapper.IToolCodeGenTableColumnMapper;
import com.wick.boot.module.tools.mapper.IToolCodeGenTableMapper;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tools.model.vo.column.AddToolCodeGEnTableColumnReqVO;
import com.wick.boot.module.tools.model.vo.table.AddToolCodeGenTableReqVO;
import com.wick.boot.module.tools.model.vo.table.QueryToolCodeGenTablePageReqVO;
import com.wick.boot.module.tools.model.vo.table.UpdateToolCodeGenReqVO;
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

    @Resource
    private ToolCodeGenConfig toolCodeGenConfig;

    @Resource
    private ToolCodeGenEngine toolCodeGenEngine;

    @Override
    public PageResult<ToolCodeGenTablePageReqsDTO> selectDbTableList(QueryToolCodeGenTablePageReqVO queryVO) {
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
        List<ToolCodeGenTable> toolCodeGenTables = Lists.newArrayList();
        for (ToolCodeGenTableDTO toolCodeGenTableDTO : toolCodeGenTableDTOS) {
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
    public PageResult<ToolCodeGenTablePageReqsDTO> selectCodeGenTableList(QueryToolCodeGenTablePageReqVO queryVO) {
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
    public void update(UpdateToolCodeGenReqVO updateVO) {
        /* Step-1: 校验更新参数信息 */
        this.validateUpdateParams(updateVO);

        /* Step-2: 更新数据 */
        // 更新 ToolCodeGenTable 数据
        AddToolCodeGenTableReqVO table = updateVO.getTable();
        ToolCodeGenTable codeGenTable = BeanUtil.copyProperties(table, ToolCodeGenTable.class);
        this.codeGenTableMapper.updateById(codeGenTable);
        // 更新 ToolCodeGEnTableColumn 数据
        List<AddToolCodeGEnTableColumnReqVO> sourceColumns = updateVO.getColumns();
        List<ToolCodeGenTableColumn> targetColumns = BeanUtil.copyToList(sourceColumns, ToolCodeGenTableColumn.class);
        targetColumns.forEach(column -> this.codeGenTableColumnMapper.updateById(column));
    }

    @Override
    public List<ToolCodeGenPreviewDTO> previewCode(Long tableId) {
        /* Step-1: 校验参数 */
        this.validatePreviewParams(tableId);

        /* Step-2: 查询*/
        List<ToolCodeGenTable> subTables = null;
        List<List<ToolCodeGenTableColumn>> subColumnsList = null;
        ToolCodeGenTable table = this.codeGenTableMapper.selectById(tableId);
        List<ToolCodeGenTableColumn> columns = this.codeGenTableColumnMapper.selectListByTableId(tableId);

        /* Step-2: 执行代码生成器模板引擎 */
        return toolCodeGenEngine.execute(table, columns, null, null);
    }
}
