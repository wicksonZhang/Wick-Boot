package com.wick.boot.module.tools.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.ToolCodeGenTable;
import com.wick.boot.module.tools.model.vo.table.QueryToolCodeGenTablePageReqVO;
import org.apache.commons.lang3.ArrayUtils;
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
public interface IToolCodeGenTableMapper extends BaseMapperX<ToolCodeGenTable> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    Page<ToolCodeGenTable> selectDataSourcePage(Page<ToolCodeGenTable> page, @Param("queryVO") QueryToolCodeGenTablePageReqVO queryVO);

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
     * 在表中数据源的数量
     *
     * @param tableNames 表名集合
     * @return 表明
     */
    List<ToolCodeGenTableDTO> selectByTableNames(@Param("tableNames") List<String> tableNames);

    /**
     * 分页查询代码生成器表数据
     *
     * @param reqVO 查询VO信息
     * @return 分页数据
     */
    default Page<ToolCodeGenTable> selectCodeGenTablePage(QueryToolCodeGenTablePageReqVO reqVO) {
        LambdaQueryWrapper<ToolCodeGenTable> wrapper = new LambdaQueryWrapper<>();
        Object startTime = ArrayUtils.get(reqVO.getCreateTime(), 0);
        Object endTime = ArrayUtils.get(reqVO.getCreateTime(), 1);
        wrapper.likeRight(ObjUtil.isNotEmpty(reqVO.getTableName()), ToolCodeGenTable::getTableName, reqVO.getTableName())
                .likeRight(ObjUtil.isNotEmpty(reqVO.getTableComment()), ToolCodeGenTable::getTableComment, reqVO.getTableComment())
                .between(ObjUtil.isAllNotEmpty(startTime, endTime), ToolCodeGenTable::getCreateTime, startTime, endTime)
                .orderByDesc(ToolCodeGenTable::getId);
        return selectPage(new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()), wrapper);
    }
}
