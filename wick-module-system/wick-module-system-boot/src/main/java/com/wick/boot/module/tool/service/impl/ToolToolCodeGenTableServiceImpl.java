package com.wick.boot.module.tool.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tool.config.ToolCodeGenConfig;
import com.wick.boot.module.tool.convert.ToolCodeGenTableConvert;
import com.wick.boot.module.tool.engine.ToolCodeGenEngine;
import com.wick.boot.module.tool.mapper.ToolCodeGenTableColumnMapper;
import com.wick.boot.module.tool.mapper.ToolCodeGenTableMapper;
import com.wick.boot.module.tool.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tool.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tool.model.vo.column.ToolCodeGenTableColumnAddVO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableAddVO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableQueryVO;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableUpdateVO;
import com.wick.boot.module.tool.service.ToolCodeGenTableAbstractService;
import com.wick.boot.module.tool.service.ToolCodeGenTableService;
import com.wick.boot.module.tool.utils.ToolCodeGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        Map<String, List<ToolCodeGenTableColumn>> columnCache = new HashMap<>();
        for (ToolCodeGenTable toolCodeGenTable : toolCodeGenTables) {
            String tableName = toolCodeGenTable.getTableName();
            // 查询字段信息，使用缓存
            List<ToolCodeGenTableColumn> columns = columnCache.computeIfAbsent(tableName, key ->
                    this.codeGenTableColumnMapper.selectDbTableColumnsByName(key)
            );
            for (ToolCodeGenTableColumn column : columns) {
                column.setTableId(toolCodeGenTable.getId());
                // 初始化部分字段信息
                ToolCodeGenUtils.initColumnField(column);
                saveColumns.add(column);
            }
        }
        this.codeGenTableColumnMapper.insertBatch(saveColumns);
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
        // Step 1: 校验参数
        // 查询并校验数据表信息
        ToolCodeGenTable codeGenTable = this.codeGenTableMapper.selectById(tableId);
        this.validateCodeGenTable(codeGenTable);

        // 查询并校验数据表字段
        List<ToolCodeGenTableColumn> targetColumns = this.codeGenTableColumnMapper.selectListByTableId(tableId);
        this.validateCodeGenTableColumn(targetColumns);

        // 从数据库中查询源数据表字段
        List<ToolCodeGenTableColumn> sourceColumns = this.codeGenTableColumnMapper.selectDbTableColumnsByName(codeGenTable.getTableName());
        this.validateCodeGenTableColumn(sourceColumns);

        // Step 2: 根据新增、更新、删除分类处理
        // 新增字段处理：在 sourceColumns 中存在，但 targetColumns 中不存在的字段
        insertColumns(targetColumns, sourceColumns, codeGenTable.getId());

        // 更新字段处理：sourceColumns 和 targetColumns 中都存在的字段，进行更新
        updateColumns(targetColumns, sourceColumns);

        // 删除字段处理：在 targetColumns 中存在，但 sourceColumns 中不存在的字段
        deleteColumns(targetColumns, sourceColumns);
    }

    /**
     * 处理新增字段
     *
     * @param targetColumns 目标字段集合
     * @param sourceColumns 源字段集合
     * @param tableId       表 ID
     */
    private void insertColumns(List<ToolCodeGenTableColumn> targetColumns, List<ToolCodeGenTableColumn> sourceColumns, Long tableId) {
        // 获取 sourceColumns 中存在但 targetColumns 中不存在的字段名
        List<String> insertColumns = getSubtractFiled(sourceColumns, targetColumns);

        if (CollUtil.isEmpty(insertColumns)) {
            return;
        }

        // 准备新增字段
        List<ToolCodeGenTableColumn> saveList = new ArrayList<>();
        sourceColumns.stream()
                .filter(columns -> insertColumns.contains(columns.getColumnName()))
                .forEach(columns -> {
                    columns.setTableId(tableId);
                    ToolCodeGenUtils.initColumnField(columns);  // 初始化字段信息
                    saveList.add(columns);
                });

        // 批量插入新字段
        this.codeGenTableColumnMapper.insertBatch(saveList);
    }

    /**
     * 处理更新字段
     *
     * @param targetColumns 目标字段集合
     * @param sourceColumns 源字段集合
     */
    private void updateColumns(List<ToolCodeGenTableColumn> targetColumns, List<ToolCodeGenTableColumn> sourceColumns) {
        // 获取 sourceColumns 和 targetColumns 的交集字段名
        Set<String> updateColumns = getIntersectionFiled(targetColumns, sourceColumns);

        if (CollUtil.isEmpty(updateColumns)) {
            return;
        }

        // 将 targetColumns 转为 Map，方便通过字段名获取字段
        Map<String, ToolCodeGenTableColumn> targetColumnMap = targetColumns.stream()
                .collect(Collectors.toMap(ToolCodeGenTableColumn::getColumnName, Function.identity()));

        // 准备更新字段
        List<ToolCodeGenTableColumn> updateList = new ArrayList<>();
        sourceColumns.stream()
                .filter(column -> updateColumns.contains(column.getColumnName()))
                .forEach(column -> {
                    ToolCodeGenUtils.initColumnField(column);  // 初始化字段信息
                    ToolCodeGenTableColumn prevColumn = targetColumnMap.get(column.getColumnName());

                    // 设置字段的 ID 和表 ID，保留部分旧属性
                    column.setId(prevColumn.getId());
                    column.setTableId(prevColumn.getTableId());
                    if (column.isPage()) {
                        column.setDictType(prevColumn.getDictType());
                        column.setQueryType(prevColumn.getQueryType());
                    }
                    if (StrUtil.isNotEmpty(prevColumn.getRequired()) && !column.isPrimaryKey()
                            && (column.isInsert() || column.isUpdate())
                            && (column.isUsableColumn() || !column.isSuperColumn())) {
                        column.setRequired(prevColumn.getRequired());
                        column.setHtmlType(prevColumn.getHtmlType());
                    }
                    updateList.add(column);
                });

        // 批量更新字段
        this.codeGenTableColumnMapper.updateBatch(updateList);
    }

    /**
     * 处理删除字段
     *
     * @param targetColumns 目标字段集合
     * @param sourceColumns 源字段集合
     */
    private void deleteColumns(List<ToolCodeGenTableColumn> targetColumns, List<ToolCodeGenTableColumn> sourceColumns) {
        // 获取 targetColumns 中存在但 sourceColumns 中不存在的字段名
        List<String> deleteColumns = getSubtractFiled(targetColumns, sourceColumns);

        if (CollUtil.isEmpty(deleteColumns)) {
            return;
        }

        // 准备删除字段
        List<ToolCodeGenTableColumn> deleteList = new ArrayList<>();
        targetColumns.stream()
                .filter(column -> deleteColumns.contains(column.getColumnName()))
                .forEach(deleteList::add);

        // 批量删除字段
        this.codeGenTableColumnMapper.deleteBatchIds(deleteList);
    }

    /**
     * 获取差集字段名：sourceColumns - targetColumns
     *
     * @param sourceColumns 源字段集合
     * @param targetColumns 目标字段集合
     * @return 差集字段名列表
     */
    private List<String> getSubtractFiled(List<ToolCodeGenTableColumn> sourceColumns, List<ToolCodeGenTableColumn> targetColumns) {
        List<String> sourceColumnNames = sourceColumns.stream().map(ToolCodeGenTableColumn::getColumnName).collect(Collectors.toList());
        List<String> targetColumnNames = targetColumns.stream().map(ToolCodeGenTableColumn::getColumnName).collect(Collectors.toList());

        // 获取差集字段名
        return CollectionUtil.subtractToList(sourceColumnNames, targetColumnNames);
    }

    /**
     * 获取交集字段名：sourceColumns ∩ targetColumns
     *
     * @param targetColumns 目标字段集合
     * @param sourceColumns 源字段集合
     * @return 交集字段名集合
     */
    private Set<String> getIntersectionFiled(List<ToolCodeGenTableColumn> targetColumns, List<ToolCodeGenTableColumn> sourceColumns) {
        List<String> targetColumnNames = targetColumns.stream().map(ToolCodeGenTableColumn::getColumnName).collect(Collectors.toList());
        List<String> sourceColumnNames = sourceColumns.stream().map(ToolCodeGenTableColumn::getColumnName).collect(Collectors.toList());

        // 获取交集字段名
        return CollectionUtil.intersectionDistinct(sourceColumnNames, targetColumnNames);
    }

    @Override
    public void download(HttpServletResponse response, Long tableId) {
        byte[] data = downloadCode(tableId);
        String downloadFileName = toolCodeGenConfig.getDownloadFileName();

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downloadFileName, "UTF-8"));
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            log.error("Error while writing the zip file to response", e);
            throw new RuntimeException("Failed to write the zip file to response", e);
        }
    }

    /**
     * 下载代码
     *
     * @param tableId 数据表Id
     * @return 压缩文件字节数组
     */
    private byte[] downloadCode(Long tableId) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ZipOutputStream zip = new ZipOutputStream(outputStream)) {

            // 遍历每个表名，生成对应的代码并压缩到 zip 文件中
            generateAndZipCode(tableId, zip);
            // 确保所有压缩数据写入输出流，避免数据残留在内存缓冲区引发的数据不完整
            zip.finish();
            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("Error while generating zip for code download", e);
            throw new RuntimeException("Failed to generate code zip file", e);
        }
    }

    /**
     * 根据表名生成代码并压缩到zip文件中
     *
     * @param tableId 表名
     * @param zip     压缩文件输出流
     */
    private void generateAndZipCode(Long tableId, ZipOutputStream zip) {
        List<ToolCodeGenPreviewDTO> codePreviewList = previewCode(tableId);

        for (ToolCodeGenPreviewDTO codePreview : codePreviewList) {
            String fileName = codePreview.getFileName();
            String content = codePreview.getContent();
            String path = codePreview.getPath();

            try {
                // 创建压缩条目
                ZipEntry zipEntry = new ZipEntry(path + File.separator + fileName);
                zip.putNextEntry(zipEntry);

                // 写入文件内容
                zip.write(content.getBytes(StandardCharsets.UTF_8));

                // 关闭当前压缩条目
                zip.closeEntry();

            } catch (IOException e) {
                log.error("Error while adding file {} to zip", fileName, e);
            }
        }
    }
}
