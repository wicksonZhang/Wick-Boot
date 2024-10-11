package com.wick.boot.module.tool.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTableColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 代码自动生成器-字段信息
 *
 * @author Wickson
 * @date 2024-07-25
 */
@Mapper
public interface ToolCodeGenTableColumnMapper extends BaseMapperX<ToolCodeGenTableColumn> {

    /**
     * 通过表名查询数据表字段信息
     *
     * @param tableName 表名
     * @return 表字段信息
     */
    List<ToolCodeGenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * 通过表id获取数据表结构
     *
     * @param tableId 数据表id
     * @return 数据表结构集合
     */
    default List<ToolCodeGenTableColumn> selectListByTableId(Long tableId) {
        return selectList(new LambdaQueryWrapper<ToolCodeGenTableColumn>()
                .eq(ToolCodeGenTableColumn::getTableId, tableId)
                .orderByAsc(ToolCodeGenTableColumn::getId));

    }
}
