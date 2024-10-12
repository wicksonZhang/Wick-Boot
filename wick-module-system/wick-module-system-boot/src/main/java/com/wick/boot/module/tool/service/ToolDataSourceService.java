package com.wick.boot.module.tool.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceAddVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceUpdateVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceQueryVO;
import com.wick.boot.module.tool.model.dto.datasource.ToolDataSourceDTO;

import java.util.List;

/**
 * 数据源配置管理-服务类
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
public interface ToolDataSourceService {

    /**
     * 新增数据源配置数据
     *
     * @param reqVO 新增请求参数
     */
    Long addToolDataSource(ToolDataSourceAddVO reqVO);

    /**
     * 更新数据源配置数据
     *
     * @param reqVO 更新请求参数
     */
    void updateToolDataSource(ToolDataSourceUpdateVO reqVO);

    /**
     * 删除新增数据源配置数据
     *
     * @param ids 主键集合
     */
    void deleteToolDataSource(List<Long> ids);

    /**
     * 通过主键获取数据源配置数据
     *
     * @param id 数据源配置ID
     * @return ToolDataSourceDTO 数据源配置DTO
     */
     ToolDataSourceDTO getToolDataSource(Long id);

    /**
     * 获取数据源配置分页数据
     *
     * @param queryParams 分页查询参数
     * @return ToolDataSourceDTO 数据源配置DTO
     */
    PageResult<ToolDataSourceDTO> getToolDataSourcePage(ToolDataSourceQueryVO queryParams);
}