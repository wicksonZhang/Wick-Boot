package com.wick.boot.module.tool.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tool.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tool.model.vo.table.ToolCodeGenTableQueryVO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代码生成器 Mapper
 *
 * @author Wickson
 * @date 2024-07-23
 */
@Mapper
public interface ToolCodeGenTableMapper extends BaseMapperX<ToolCodeGenTable> {

    /**
     * 获取数据表集合
     *
     * @param tableNames 数据表集合
     * @return 表集合
     */
    default List<ToolCodeGenTable> selectTables(List<String> tableNames) {
        return selectList(new LambdaQueryWrapper<ToolCodeGenTable>().in(ToolCodeGenTable::getTableName, tableNames));
    }

    /**
     * 分页查询代码生成器表数据
     *
     * @param reqVO 查询VO信息
     * @return 分页数据
     */
    default Page<ToolCodeGenTable> selectCodeGenTablePage(ToolCodeGenTableQueryVO reqVO) {
        LambdaQueryWrapper<ToolCodeGenTable> wrapper = new LambdaQueryWrapper<>();
        Object startTime = ArrayUtils.get(reqVO.getCreateTime(), 0);
        Object endTime = ArrayUtils.get(reqVO.getCreateTime(), 1);
        wrapper.likeRight(ObjUtil.isNotEmpty(reqVO.getTableName()), ToolCodeGenTable::getTableName, reqVO.getTableName())
                .likeRight(ObjUtil.isNotEmpty(reqVO.getTableComment()), ToolCodeGenTable::getTableComment, reqVO.getTableComment())
                .between(ObjUtil.isAllNotEmpty(startTime, endTime), ToolCodeGenTable::getCreateTime, startTime, endTime)
                .orderByDesc(ToolCodeGenTable::getId);
        return selectPage(new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()), wrapper);
    }

    default Set<String> selectListByTableNames(Long dataSourceId) {
        // 拼接查询条件
        LambdaQueryWrapper<ToolCodeGenTable> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(ToolCodeGenTable::getTableName).eq(ToolCodeGenTable::getDataSourceId, dataSourceId);
        // 查询集合
        List<ToolCodeGenTable> toolCodeGenTables = this.selectList(wrapper);
        return toolCodeGenTables.stream().map(ToolCodeGenTable::getTableName).collect(Collectors.toSet());
    }
}
