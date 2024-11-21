package com.wick.boot.module.okx.api.config.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.common.core.result.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.module.okx.api.config.convert.ApiConfigConvert;
import com.wick.boot.module.okx.api.config.mapper.ApiConfigMapper;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigAddVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigQueryVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigUpdateVO;
import com.wick.boot.module.okx.api.config.service.ApiConfigAbstractService;
import com.wick.boot.module.okx.api.config.service.ApiConfigService;
import com.wick.boot.module.okx.api.config.model.dto.apiconfig.ApiConfigDTO;
import com.wick.boot.module.okx.api.config.model.entity.ApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Api配置管理-服务实现类
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Slf4j
@Service
public class ApiConfigServiceImpl extends ApiConfigAbstractService implements ApiConfigService {

    @Resource
    private ApiConfigMapper apiConfigMapper;

    /**
     * 新增Api配置数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addApiConfig(ApiConfigAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 转换实体类型 */
        ApiConfig apiConfig = ApiConfigConvert.INSTANCE.addVoToEntity(reqVO);

        /* Step-3: 保存Api配置信息 */
        this.apiConfigMapper.insert(apiConfig);
        return apiConfig.getId();
    }

    /**
     * 更新Api配置数据
     *
     * @param reqVO 更新请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApiConfig(ApiConfigUpdateVO reqVO) {
        /* Step-1: 校验更新参数 */
        this.validateUpdateParams(reqVO);

        /* Step-2: 转换实体类型 */
        ApiConfig apiConfig = ApiConfigConvert.INSTANCE.updateVoToEntity(reqVO);

        /* Step-3: 更新Api配置信息 */
        this.apiConfigMapper.updateById(apiConfig);
    }

    /**
     * 删除Api配置数据
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApiConfig(List<Long> ids) {
        /* Step-1: 校验删除参数 */
        List<ApiConfig> apiConfigList = this.apiConfigMapper.selectBatchIds(ids);
        this.validateDeleteParams(apiConfigList, ids);

        /* Step-2: 删除Api配置信息 */
        this.apiConfigMapper.deleteBatchIds(apiConfigList);
    }

    /**
     * 获取Api配置数据
     *
     * @param id Api配置ID
     * @return ApiConfigDTO Api配置DTO
     */
    public ApiConfigDTO getApiConfig(Long id) {
        /* Step-1: 通过主键获取Api配置 */
        ApiConfig apiConfig = this.apiConfigMapper.selectById(id);

        /* Step-2: 转换实体类型 */
        return ApiConfigConvert.INSTANCE.entityToDTO(apiConfig);
    }

    /**
     * 获取Api配置分页数据
     *
     * @param queryParams 分页查询参数
     * @return ApiConfigDTO Api配置DTO
     */
    public PageResult<ApiConfigDTO> getApiConfigPage(ApiConfigQueryVO queryParams) {
        Page<ApiConfig> pageResult = this.apiConfigMapper.getApiConfigPage(
                new Page<>(queryParams.getPageNumber(), queryParams.getPageSize()),
                queryParams
        );

        if (ObjUtil.isNull(pageResult)) {
            return PageResult.empty();
        }

        List<ApiConfigDTO> apiConfigPages = ApiConfigConvert.INSTANCE.entityToPage(pageResult.getRecords());
        return new PageResult<>(apiConfigPages, pageResult.getTotal());
    }

    @Override
    public ApiConfig getApiConfigByUserId(Long userId) {
        return this.apiConfigMapper.selectByCreateBy(userId);
    }
}