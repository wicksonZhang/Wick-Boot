package com.wick.boot.module.tools.mapper;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.entity.CodeGenTable;
import com.wick.boot.module.tools.model.vo.QueryCodeGenTablePageReqVO;
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
public interface ICodeGenTableMapper extends BaseMapperX<CodeGenTable> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    Page<CodeGenTable> selectDataSourcePage(Page<CodeGenTable> page, @Param("queryVO") QueryCodeGenTablePageReqVO queryVO);

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

    /**
     * 分页查询代码生成器表数据
     *
     * @param reqVO 查询VO信息
     * @return 分页数据
     */
    default Page<CodeGenTable> selectCodeGenTablePage(QueryCodeGenTablePageReqVO reqVO) {
        LambdaQueryWrapper<CodeGenTable> wrapper = new LambdaQueryWrapper<>();
        Object startTime = ArrayUtils.get(reqVO.getCreateTime(), 0);
        Object endTime = ArrayUtils.get(reqVO.getCreateTime(), 1);
        wrapper.likeRight(ObjUtil.isNotEmpty(reqVO.getName()), CodeGenTable::getTableName, reqVO.getName())
                .likeRight(ObjUtil.isNotEmpty(reqVO.getComment()), CodeGenTable::getTableComment, reqVO.getComment())
                .between(ObjUtil.isAllNotEmpty(startTime, endTime), CodeGenTable::getCreateTime, startTime, endTime)
                .orderByDesc(CodeGenTable::getId);
        return selectPage(new Page<>(reqVO.getPageNumber(), reqVO.getPageSize()), wrapper);
    }
}
