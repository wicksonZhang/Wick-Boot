package com.wick.boot.module.tool.convert;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.wick.boot.module.tool.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tool.model.dto.column.ToolCodeGenColumnDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tool.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 代码自动生成器-Convert
 *
 * @author Wickson
 * @date 2024-07-23
 */
@Mapper
public interface ToolCodeGenTableConvert {

    ToolCodeGenTableConvert INSTANCE = Mappers.getMapper(ToolCodeGenTableConvert.class);

    /**
     * Convert entity To DTO
     *
     * @param records 代码生成器集合
     * @return 代码生成器集合DTO
     */
    List<ToolCodeGenTablePageReqsDTO> entityToCodeGenDTOS(List<ToolCodeGenTable> records);

    /**
     * 设置表信息
     *
     * @param table   表信息
     * @param columns 表结构集合信息
     * @return 表详细信息
     */
    default ToolCodeGenDetailDTO convertDetailDTO(ToolCodeGenTable table, List<ToolCodeGenTableColumn> columns) {
        ToolCodeGenDetailDTO respVO = new ToolCodeGenDetailDTO();
        respVO.setTable(BeanUtil.copyProperties(table, ToolCodeGenTableDTO.class));
        respVO.setColumns(BeanUtil.copyToList(columns, ToolCodeGenColumnDTO.class));
        return respVO;
    }

    /**
     * TableInfo 转 ToolCodeGenTable
     *
     * @param tableInfo 元数据表信息
     * @return ToolCodeGenTable 实体
     */
    @Mappings({
            @Mapping(target = "tableName", source = "name"),
            @Mapping(target = "tableComment", source = "comment")
    })
    ToolCodeGenTable toToolCodeGenTable(TableInfo tableInfo);

    /**
     * tableInfos 转 ToolCodeGenTablePageReqsDTO
     *
     * @param tableInfos 分页集合
     * @return
     */
    List<ToolCodeGenTablePageReqsDTO> toToolCodeGenTableList(List<TableInfo> tableInfos);

    @Mappings({
            @Mapping(target = "tableName", source = "name"),
            @Mapping(target = "tableComment", source = "comment")
    })
    ToolCodeGenTablePageReqsDTO toToolCodeGenTablePage(TableInfo tableInfo);
}
