package com.wick.boot.module.tools.app.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.model.dto.ToolCodeGenDetailDTO;
import com.wick.boot.module.tools.model.dto.table.ToolCodeGenTablePageReqsDTO;
import com.wick.boot.module.tools.model.vo.table.QueryToolCodeGenTablePageReqVO;

import java.util.List;

/**
 * 代码自动生成器 - 服务层
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
public interface IToolCodeGenTableService {

    /**
     * 获取数据源分页数据信息
     *
     * @param queryVO 查询参数VO
     * @return 分页数据
     */
    PageResult<ToolCodeGenTablePageReqsDTO> selectDbTableList(QueryToolCodeGenTablePageReqVO queryVO);

    /**
     * 导入数据表
     *
     * @param tableNames 表名
     */
    void importTable(List<String> tableNames);

    /**
     * 获取代码生成器分页数据
     *
     * @param queryVO 查询参数
     * @return 分页数据
     */
    PageResult<ToolCodeGenTablePageReqsDTO> selectCodeGenTableList(QueryToolCodeGenTablePageReqVO queryVO);

    /**
     * 通过数据表Id查询数据表详细信息
     *
     * @param tableId 数据表Id
     * @return
     */
    ToolCodeGenDetailDTO getDetails(Long tableId);
}
