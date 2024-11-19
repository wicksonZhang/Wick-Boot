package com.wick.boot.module.okx.api.config.convert;

import cn.hutool.core.util.ObjUtil;
import com.wick.boot.module.okx.api.config.model.entity.ApiConfig;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigAddVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigQueryVO;
import com.wick.boot.module.okx.api.config.model.vo.apiconfig.ApiConfigUpdateVO;
import com.wick.boot.module.okx.api.config.model.dto.apiconfig.ApiConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Api配置管理-转换类
 *
 * @author Wickson
 * @date 2024-11-18 10:42
 */
@Mapper
public interface ApiConfigConvert {

    ApiConfigConvert INSTANCE = Mappers.getMapper(ApiConfigConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return ApiApiConfig Api配置
     */
     ApiConfig addVoToEntity(ApiConfigAddVO reqVO);

    /**
     * Convert updateVo To entity
     *
     * @param reqVO 更新请求参数VO
     * @return ApiApiConfig Api配置实体
     */
     ApiConfig updateVoToEntity(ApiConfigUpdateVO reqVO);

    /**
     * Convert entity to DTO
     *
     * @param apiConfig Api配置实体
     * @return ApiConfigDTO
     */
     ApiConfigDTO entityToDTO(ApiConfig apiConfig);

    /**
     * Convert entity to DTOList
     *
     * @param apiConfigList Api配置实体集合
     * @return List<ApiConfigDTO> ApiConfigDTO集合对象
     */
    List<ApiConfigDTO> entityToPage(List<ApiConfig> apiConfigList);

}