package com.wick.boot.module.tools.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tools.model.entity.CodeGenTableColumn;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 代码自动生成器-字段信息
 *
 * @author ZhangZiHeng
 * @date 2024-07-25
 */
@Mapper
public interface ICodeGenTableColumnMapper extends BaseMapperX<CodeGenTableColumn> {

    /**
     * 通过表名查询数据表字段信息
     *
     * @param tableName 表名
     * @return 表字段信息
     */
    List<CodeGenTableColumn> selectDbTableColumnsByName(String tableName);
}
