package com.wick.boot.module.tool.convert;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import org.apache.ibatis.type.JdbcType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 代码自动生成器表字段信息-Convert
 *
 * @author Wickson
 * @date 2024-07-25
 */
@Mapper
public interface ToolCodeGenTableColumnConvert {

    ToolCodeGenTableColumnConvert INSTANCE = Mappers.getMapper(ToolCodeGenTableColumnConvert.class);

    @Mappings({
            @Mapping(target = "columnName", source = "name"),
            @Mapping(target = "columnComment", source = "comment"),
            @Mapping(target = "javaField", source = "propertyName"),
            @Mapping(target = "increment", expression = "java(convertKeyIdentityFlag(field))"),
            @Mapping(target = "pk", expression = "java(convertKeyFlag(field))"),
            @Mapping(target = "columnType", expression = "java(convertColumnType(field))"),
            @Mapping(target = "required", expression = "java(convertRequired(field))"),
    })
    ToolCodeGenTableColumn toToolCodeGenTable(TableField field);

    default String convertKeyIdentityFlag(TableField field) {
        return field.isKeyIdentityFlag() ? "1" : "0";
    }

    default String convertKeyFlag(TableField field) {
        return field.isKeyFlag() ? "1" : "0";
    }

    /**
     * 获取列类型
     *
     * @param tableField 数据表字段信息
     * @return 列类型
     */
    default String convertColumnType(TableField tableField) {
        if (tableField == null) {
            return null;
        }
        TableField.MetaInfo metaInfo = tableField.getMetaInfo();
        if (metaInfo == null) {
            return null;
        }
        JdbcType jdbcType = metaInfo.getJdbcType();
        if (jdbcType == null) {
            return null;
        }
        return jdbcType.name().toLowerCase();
    }

    /**
     * 是否必填
     *
     * @return 1 Or 0
     */
    default String convertRequired(TableField tableField) {
        if (tableField == null) {
            return null;
        }
        TableField.MetaInfo metaInfo = tableField.getMetaInfo();
        if (metaInfo == null) {
            return null;
        }
        return metaInfo.isNullable() ? "1" : "0";
    }

}
