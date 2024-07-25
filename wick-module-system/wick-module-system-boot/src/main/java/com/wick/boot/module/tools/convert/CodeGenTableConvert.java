package com.wick.boot.module.tools.convert;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.wick.boot.module.tools.config.CodeGenConfig;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.CodeGenTable;
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
public interface CodeGenTableConvert {

    CodeGenTableConvert INSTANCE = Mappers.getMapper(CodeGenTableConvert.class);

    /**
     * Convert entity To DTO
     *
     * @param records 代码生成器集合
     * @return 代码生成器集合DTO
     */
    List<CodeGenTableDTO> entityToCodeGenDTOS(List<CodeGenTable> records);

    /**
     * Convert DTO To Entity
     *
     * @param codeGenTableDTOS 代码生成器DTOS
     * @return 代码生成器集合
     */
    List<CodeGenTable> dtoToEntity(List<CodeGenTableDTO> codeGenTableDTOS);

    @Mappings({
            @Mapping(target = "className", expression = "java(convertClassName(tableDTO))"),
            @Mapping(target = "packageName", expression = "java(convertPackageName())"),
            @Mapping(target = "moduleName", expression = "java(convertModuleName())"),
            @Mapping(target = "businessName", expression = "java(convertBusinessName(tableDTO))"),
            @Mapping(target = "functionName", expression = "java(convertFunctionName(tableDTO))"),
            @Mapping(target = "functionAuthor", expression = "java(convertFunctionAuthor())")
    })
    CodeGenTable toEntity(CodeGenTableDTO tableDTO);

    default String convertClassName(CodeGenTableDTO tableDTO) {
        return NamingCase.toPascalCase(tableDTO.getTableName());
    }

    default String convertPackageName() {
        return CodeGenConfig.getPackageName();
    }

    default String convertModuleName() {
        String packageName = CodeGenConfig.getPackageName();
        return StrUtil.sub(packageName, packageName.length() + 1, packageName.length());
    }

    default String convertBusinessName(CodeGenTableDTO tableDTO) {
        String tableName = tableDTO.getTableName();
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StrUtil.sub(tableName, lastIndex + 1, nameLength);
    }

    default String convertFunctionName(CodeGenTableDTO tableDTO) {
        return tableDTO.getTableComment();
    }

    default String convertFunctionAuthor() {
        return CodeGenConfig.getAuthor();
    }

}
