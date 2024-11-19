package com.wick.boot.module.okx.api.config.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.okx.api.config.mapper.ApiConfigMapper;
import com.wick.boot.module.okx.api.config.model.entity.ApiConfig;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigAddVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigUpdateVO;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Api配置管理-防腐层
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
public abstract class ApiConfigAbstractService {

    @Resource
    private ApiConfigMapper apiConfigMapper;

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(ApiConfigAddVO reqVO) {

    }

    /**
     * 校验更新参数
     *
     * @param reqVO 新增参数
     */
    protected void validateUpdateParams(ApiConfigUpdateVO reqVO) {

    }

    /**
     * 校验删除参数
     *
     * @param apiConfigList 新增集合参数
     * @param ids                   主键集合
     */
    protected void validateDeleteParams(List<ApiConfig> apiConfigList, List<Long> ids) {
        // 验证Api配置否存在
        this.validateApiConfigList(apiConfigList);
        // 验证Api配置集合和 ids 是否匹配
        this.validateApiConfigByIds(apiConfigList, ids);
    }

    private void validateApiConfigList(List<ApiConfig> apiConfigList) {
        // 验证Api配置否存在
        if (CollUtil.isEmpty(apiConfigList)) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
        }
    }

    private void validateApiConfigByIds(List<ApiConfig> apiConfigList, List<Long> ids) {
        List<Long> apiConfigIds = apiConfigList.stream().map(ApiConfig::getId).collect(Collectors.toList());
        Collection<Long> errorIds = CollectionUtil.subtract(ids, apiConfigIds);
        if (CollUtil.isNotEmpty(errorIds)) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.PARAM_IS_INVALID);
        }
    }
}