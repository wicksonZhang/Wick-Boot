package com.wick.boot.module.tools.convert;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.tools.config.ToolCodeGenConfig;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.column.ToolCodeGenColumnDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 代码自动生成器-Convert
 *
 * @author ZhangZiHeng
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
     * Convert DTO To Entity
     *
     * @param toolCodeGenTableDTOS 代码生成器DTOS
     * @return 代码生成器集合
     */
    List<ToolCodeGenTable> dtoToEntity(List<ToolCodeGenTableDTO> toolCodeGenTableDTOS);

    @Mappings({
            @Mapping(target = "className", expression = "java(convertClassName(tableDTO))"),
            @Mapping(target = "packageName", expression = "java(convertPackageName())"),
            @Mapping(target = "moduleName", expression = "java(convertModuleName())"),
            @Mapping(target = "businessName", expression = "java(convertBusinessName(tableDTO))"),
            @Mapping(target = "functionName", expression = "java(convertFunctionName(tableDTO))"),
            @Mapping(target = "functionAuthor", expression = "java(convertFunctionAuthor())")
    })
    ToolCodeGenTable toEntity(ToolCodeGenTableDTO tableDTO);

    /**
     * 设置类名
     *
     * @param tableDTO 数据表
     * @return 类名
     */
    default String convertClassName(ToolCodeGenTableDTO tableDTO) {
        return NamingCase.toPascalCase(tableDTO.getTableName());
    }

    /**
     * 设置包名
     *
     * @return 包名
     */
    default String convertPackageName() {
        return ToolCodeGenConfig.getPackageName();
    }

    /**
     * 设置模块名
     *
     * @return 模块名
     */
    default String convertModuleName() {
        String packageName = ToolCodeGenConfig.getPackageName();
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StrUtil.sub(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 设置业务名
     *
     * @param tableDTO 数据表DTO
     * @return 业务名称
     */
    default String convertBusinessName(ToolCodeGenTableDTO tableDTO) {
        String tableName = tableDTO.getTableName();
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + 1, nameLength);
    }

    /**
     * 设置功能名
     *
     * @param tableDTO 数据表DTO
     * @return 功能名
     */
    default String convertFunctionName(ToolCodeGenTableDTO tableDTO) {
        return tableDTO.getTableComment();
    }

    /**
     * 设置作者名称
     *
     * @return 作者名称
     */
    default String convertFunctionAuthor() {
        return ToolCodeGenConfig.getAuthor();
    }

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
}
