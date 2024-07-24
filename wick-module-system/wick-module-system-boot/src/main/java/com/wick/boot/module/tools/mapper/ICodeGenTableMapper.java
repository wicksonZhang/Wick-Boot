package com.wick.boot.module.tools.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.CodeGenTable;
import com.wick.boot.module.tools.model.vo.QueryCodeGenTablePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成器 Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
@Mapper
public interface ICodeGenTableMapper extends BaseMapperX<CodeGenTable> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    Page<CodeGenTable> selectCodeGenPage(Page<CodeGenTable> page, @Param("queryVO") QueryCodeGenTablePageReqVO queryVO);

    /**
     * 获取数据表集合
     *
     * @param tableNames 数据表集合
     * @return 表集合
     */
    default List<CodeGenTable> selectTables(List<String> tableNames) {
        return selectList(new LambdaQueryWrapper<CodeGenTable>().in(CodeGenTable::getTableName, tableNames));
    }

    /**
     * 在表中数据源的数量
     *
     * @param tableNames 表名集合
     * @return 表明
     */
    List<CodeGenTableDTO> selectByTableNames(@Param("tableNames") List<String> tableNames);
}
