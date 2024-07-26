package com.wick.boot.module.tools.convert;

import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTableColumn;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 代码自动生成器表字段信息-Convert
 *
 * @author ZhangZiHeng
 * @date 2024-07-25
 */
@Mapper
public interface ToolCodeGenTableColumnConvert {

    ToolCodeGenTableColumnConvert INSTANCE = Mappers.getMapper(ToolCodeGenTableColumnConvert.class);

    List<ToolCodeGenTableColumn> dtoToEntity(List<ToolCodeGenTable> toolCodeGenTables);
}
