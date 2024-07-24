package com.wick.boot.module.tools.app.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tools.model.dto.CodeGenTableDTO;
import com.wick.boot.module.tools.model.vo.QueryCodeGenTablePageReqVO;

import java.util.List;

/**
 * 代码自动生成器 - 服务层
 *
 * @author ZhangZiHeng
 * @date 2024-07-23
 */
public interface ICodeGenTableService {

    /**
     * 获取数据源分页数据信息
     *
     * @param queryVO 查询参数VO
     * @return 分页数据
     */
    PageResult<CodeGenTableDTO> selectDbTableList(QueryCodeGenTablePageReqVO queryVO);

    /**
     * 导入数据表
     *
     * @param tableNames 表名
     */
    void importTable(List<String> tableNames);

}
