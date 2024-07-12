package com.wick.boot.module.tools.convert;

import com.wick.boot.module.tools.model.dto.DataSourceConfigDTO;
import com.wick.boot.module.tools.model.entity.DataSourceConfig;
import com.wick.boot.module.tools.model.vo.AddDataSourceConfigVO;
import com.wick.boot.module.tools.model.vo.UpdateDataSourceConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数据源配置 - 转换类
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    /**
     * Convert entity to DTOList
     *
     * @param dataSourceConfigs 数据源配置集合
     * @return List<DataSourceConfigDTO>
     */
    List<DataSourceConfigDTO> entityToDTOList(List<DataSourceConfig> dataSourceConfigs);

    /**
     * Convert addVo To Entity
     *
     * @param reqVO 数据源新增配置
     * @return DataSourceConfig
     */
    DataSourceConfig addVoToEntity(AddDataSourceConfigVO reqVO);

    /**
     * Convert updateVo To Entity
     *
     * @param reqVO 数据源更新配置
     * @return DataSourceConfig
     */
    DataSourceConfig updateVoToEntity(UpdateDataSourceConfigVO reqVO);

    /**
     * Convert DTO to Entity
     *
     * @param dataSourceConfig 数据源配置
     * @return DataSourceConfig
     */
    DataSourceConfigDTO entityToDTO(DataSourceConfig dataSourceConfig);

}
