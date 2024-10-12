package com.wick.boot.module.tool.service;

import com.wick.boot.module.tool.mapper.ToolDataSourceMapper;
import com.wick.boot.module.tool.model.entity.ToolDataSource;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceAddVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceUpdateVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源配置管理-防腐层
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
public abstract class ToolDataSourceAbstractService {

    @Resource
    private ToolDataSourceMapper toolDataSourceMapper;

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(ToolDataSourceAddVO reqVO) {

    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(ToolDataSourceUpdateVO reqVO) {

    }

    /**
     * 校验删除参数
     *
     * @param toolDataSourceList 新增参数
     * @param ids                   主键集合
     */
    protected void validateDeleteParams(List<ToolDataSource> toolDataSourceList, List<Long> ids) {

    }

}