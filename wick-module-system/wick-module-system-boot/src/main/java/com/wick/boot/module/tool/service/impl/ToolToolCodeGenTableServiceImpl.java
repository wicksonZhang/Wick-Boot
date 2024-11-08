package com.wick.boot.module.tool.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.system.enums.tool.ErrorCodeTool;
import com.wick.boot.module.system.mapper.SystemMenuMapper;
import com.wick.boot.module.system.model.entity.SystemMenu;
import com.wick.boot.module.tool.config.ToolCodeGenConfig;
import com.wick.boot.module.tool.constant.ToolCodeGenConstants;
import com.wick.boot.module.tool.convert.ToolCodeGenTableColumnConvert;
import com.wick.boot.module.tool.convert.ToolCodeGenTableConvert;
import com.wick.boot.module.tool.engine.ToolCodeGenEngine;
import com.wick.boot.module.tool.mapper.ToolCodeGenTableColumnMapper;
import com.wick.boot.module.tool.mapper.ToolCodeGenTableMapper;
import com.wick.boot.module.tool.mapper.ToolDataSourceMapper;
import com.wick.boot.module.tool.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tool.model.dto.ToolCodeGenPreviewDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import com.wick.boot.module.tool.model.entity.ToolDataSource;
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
import java.util.concurrent.atomic.AtomicInteger;
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
    private ToolDataSourceMapper dataSourceMapper;

    @Resource
    private ToolCodeGenTableColumnMapper codeGenTableColumnMapper;

    @Resource
    private ToolCodeGenConfig toolCodeGenConfig;

    @Resource
    private ToolCodeGenEngine toolCodeGenEngine;

    @Resource
    private SystemMenuMapper menuMapper;

    @Override
    public PageResult<ToolCodeGenTablePageReqsDTO> selectDbTableList(ToolCodeGenTableQueryVO queryVO) {
        // Step-1: 获取数据源下的所有表
        List<TableInfo> tableList = this.getTableInfoList(null, queryVO.getDataSourceId());

        // Step 2: 筛选符合条件的表信息
        List<TableInfo> filteredTables = filterTablesByCriteria(tableList, queryVO);

        // Step 3: 移除已存在的表，避免重复导入
        removeExistingTables(queryVO.getDataSourceId(), filteredTables);

        // Step 4: 转换表信息为DTO并返回分页结果
        return buildPageResult(filteredTables, queryVO.getPageNumber(), queryVO.getPageSize(), filteredTables.size());
    }

    /**
     * 根据请求条件过滤表信息
     *
     * @param tableList 数据表集合
     * @param reqVO     包含筛选条件的请求对象
     * @return 过滤后的表信息列表
     */
    private List<TableInfo> filterTablesByCriteria(List<TableInfo> tableList, ToolCodeGenTableQueryVO reqVO) {
        // 根据表名和表注释进行筛选
        String tableName = reqVO.getTableName();
        String tableComment = reqVO.getTableComment();
        return tableList.stream()
                .filter(table -> (StrUtil.isEmpty(tableName) || table.getName().contains(tableName)) &&
                        (StrUtil.isEmpty(tableComment) || table.getComment().contains(tableComment)))
                .collect(Collectors.toList());
    }

    /**
     * 移除已经存在的表
     *
     * @param dataSourceId 数据源ID
     * @param tableInfos   需过滤的表信息列表
     */
    private void removeExistingTables(Long dataSourceId, List<TableInfo> tableInfos) {
        // 查询已存在于数据库中的表名
        Set<String> existingTableNames = this.codeGenTableMapper.selectListByTableNames(dataSourceId);
        // 排除代码生成器的两张表
        existingTableNames.add("flyway_schema_history");
        existingTableNames.add("tool_code_gen_table");
        existingTableNames.add("tool_code_gen_table_column");
        // 移除已存在的表，避免重复处理
        tableInfos.removeIf(table -> existingTableNames.contains(table.getName()));
    }

    /**
     * 构建分页结果，将表信息转换为 DTO
     *
     * @param tables     数据表信息列表
     * @param pageNumber 当前页码
     * @param pageSize   每页显示条数
     * @param totalSize  数据总条数
     * @return 包含分页数据的结果
     */
    private PageResult<ToolCodeGenTablePageReqsDTO> buildPageResult(List<TableInfo> tables, Integer pageNumber, Integer pageSize, int totalSize) {
        int start = (pageNumber - 1) * pageSize;
        int end = Math.min(start + pageSize, tables.size());

        // 截取分页数据
        List<TableInfo> tableInfos = tables.subList(start, end);
        List<ToolCodeGenTablePageReqsDTO> dtoList = ToolCodeGenTableConvert.INSTANCE.toToolCodeGenTableList(tableInfos);

        // 构建分页结果
        return new PageResult<>(dtoList, (long) totalSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importTable(List<String> tableNames, Long dataSourceId) {
        // Step-1: 根据数据源的数据表，排除 tool_code_、flyway_ 的表信息
        List<TableInfo> tableInfoList = this.getTableInfoList(tableNames, dataSourceId);

        // Step-2: 根据数据表查询对应 code_gen_table 表中的数据
        List<ToolCodeGenTable> codeGenTables = this.codeGenTableMapper.selectTables(tableNames);
        this.validateAddParams(tableNames, tableInfoList, codeGenTables);

        // Step-3: 导入数据表
        importGenTable(tableInfoList, dataSourceId);
    }

    /**
     * 获取数据库中的表信息
     *
     * @param tableNames   指定的表名列表（可为空）
     * @param dataSourceId 数据源ID
     * @return 数据源下的表信息列表
     */
    private List<TableInfo> getTableInfoList(List<String> tableNames, Long dataSourceId) {
        // 获取数据源配置
        ToolDataSource dataSource = this.dataSourceMapper.selectById(dataSourceId);
        if (ObjUtil.isNull(dataSource)) {
            throw ServiceException.getInstance(ErrorCodeTool.TOOL_DATA_SOURCE_NOT_EXIST);
        }

        // 构建数据源和策略配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(
                dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder().enableSkipView();
        // 排除对应的表信息
        if (CollUtil.isNotEmpty(tableNames)) {
            strategyConfig.addInclude(tableNames);
        }

        // 只使用 LocalDateTime 类型，避免使用其他日期类型
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build();
        ConfigBuilder configBuilder = new ConfigBuilder(null, dataSourceConfigBuilder.build(),
                strategyConfig.build(), null, globalConfig, null);

        // 获取表信息并按表名排序
        List<TableInfo> tables = configBuilder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }

    /**
     * 导入生成表和字段信息
     *
     * @param tableList    表信息列表
     * @param dataSourceId 数据源ID
     */
    private void importGenTable(List<TableInfo> tableList, Long dataSourceId) {
        tableList.forEach(tableInfo -> {
            // 转换表信息并初始化
            ToolCodeGenTable codeGenTable = ToolCodeGenTableConvert.INSTANCE.toToolCodeGenTable(tableInfo);
            ToolCodeGenUtils.initTableField(codeGenTable, toolCodeGenConfig);
            codeGenTable.setDataSourceId(dataSourceId);
            this.codeGenTableMapper.insert(codeGenTable);

            // 插入字段信息
            List<ToolCodeGenTableColumn> saveColumns = fullTooCodeGenTableColumn(tableInfo, codeGenTable);
            this.codeGenTableColumnMapper.insertBatch(saveColumns);
        });
    }

    /**
     * 构建表字段信息列表
     *
     * @param tableInfo    表信息
     * @param codeGenTable 生成表实体
     * @return 字段信息列表
     */
    private List<ToolCodeGenTableColumn> fullTooCodeGenTableColumn(TableInfo tableInfo, ToolCodeGenTable codeGenTable) {
        List<TableField> fields = tableInfo.getFields();
        AtomicInteger index = new AtomicInteger(1);
        List<ToolCodeGenTableColumn> saveColumns = new ArrayList<>();

        fields.forEach(field -> {
            ToolCodeGenTableColumn tableColumn = ToolCodeGenTableColumnConvert.INSTANCE.toToolCodeGenTable(field);
            tableColumn.setTableId(codeGenTable.getId());
            tableColumn.setSort(index.getAndIncrement());

            // 处理字段类型和主键字段
            if (Byte.class.getSimpleName().equals(tableColumn.getJavaType())) {
                tableColumn.setJavaType(Integer.class.getSimpleName());
            }
            if (!tableInfo.isHavePrimaryKey()) {
                tableColumn.setPk("1");
            }

            ToolCodeGenUtils.initColumnField(tableColumn);
            saveColumns.add(tableColumn);
        });
        return saveColumns;
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

        // 要移除的字段名
        List<String> removeFiled = Arrays.asList(ToolCodeGenConstants.COLUMNNAME_NOT_LIST);

        // 过滤掉 removeFiled 中包含的列名
        columns.removeIf(column -> removeFiled.contains(column.getColumnName()));

        // 返回转换后的 DTO
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
        SystemMenu systemMenu = this.menuMapper.selectById(codeGenTable.getParentMenuId());
        String sort = String.valueOf(systemMenu.getSort());
        if (systemMenu.getSort() < 10) {
            sort = "0" + systemMenu.getSort();
        }
        codeGenTable.setParentMenuName(sort + "-" + systemMenu.getName());

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
        // 查询并校验数据表信息
        ToolCodeGenTable codeGenTable = this.codeGenTableMapper.selectById(tableId);
        this.validateCodeGenTable(codeGenTable);

        // 查询并校验数据表字段
        List<ToolCodeGenTableColumn> targetColumns = this.codeGenTableColumnMapper.selectListByTableId(tableId);
        this.validateCodeGenTableColumn(targetColumns);

        // 从数据库中查询源字段
        Long dataSourceId = codeGenTable.getDataSourceId();
        List<String> tableNames = Collections.singletonList(codeGenTable.getTableName());
        List<TableInfo> tableList = this.getTableInfoList(tableNames, dataSourceId);
        List<ToolCodeGenTableColumn> sourceColumns = this.fullTooCodeGenTableColumn(tableList.get(0), codeGenTable);
        this.validateCodeGenTableColumn(sourceColumns);

        // Step 2: 处理字段的新增、更新和删除
        insertColumns(targetColumns, sourceColumns, codeGenTable.getId());
        updateColumns(targetColumns, sourceColumns);
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
                        column.setDictCode(prevColumn.getDictCode());
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
