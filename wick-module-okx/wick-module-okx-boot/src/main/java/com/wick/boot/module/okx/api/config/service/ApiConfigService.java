package com.wick.boot.module.okx.api.config.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigAddVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigQueryVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigUpdateVO;
import com.wick.boot.module.okx.api.config.model.entity.ApiConfig;
import com.wick.boot.module.okx.api.config.model.dto.apiconfig.ApiConfigDTO;

import java.util.List;

/**
 * Api配置-应用服务类
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
public interface ApiConfigService {

    /**
     * 新增Api配置数据
     *
     * @param reqVO 新增请求参数
     */
    Long addApiConfig(ApiConfigAddVO reqVO);

    /**
     * 更新Api配置数据
     *
     * @param reqVO 更新请求参数
     */
    void updateApiConfig(ApiConfigUpdateVO reqVO);

    /**
     * 删除新增Api配置数据
     *
     * @param ids 主键集合
     */
    void deleteApiConfig(List<Long> ids);

    /**
     * 通过主键获取Api配置数据
     *
     * @param id Api配置ID
     * @return ApiConfigDTO Api配置DTO
     */
    ApiConfigDTO getApiConfig(Long id);

    /**
     * 获取Api配置分页数据
     *
     * @param queryParams 分页查询参数
     * @return ApiConfigDTO Api配置DTO
     */
    PageResult<ApiConfigDTO> getApiConfigPage(ApiConfigQueryVO queryParams);

    /**
     * 获取 API 配置
     *
     * @param userId 用户id
     * @return
     */
    ApiConfig getApiConfigByUserId(Long userId);
}