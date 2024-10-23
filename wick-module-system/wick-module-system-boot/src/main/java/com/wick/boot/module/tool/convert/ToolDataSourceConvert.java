package com.wick.boot.module.tool.convert;

import com.wick.boot.module.tool.model.dto.datasource.ToolDataSourceDTO;
import com.wick.boot.module.tool.model.entity.ToolDataSource;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceAddVO;
import com.wick.boot.module.tool.model.vo.datasource.ToolDataSourceUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数据源配置管理-转换类
 *
 * @author Wickson
 * @date 2024-10-12 16:34
 */
@Mapper
public interface ToolDataSourceConvert {

    ToolDataSourceConvert INSTANCE = Mappers.getMapper(ToolDataSourceConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return ToolDataSource 数据源配置
     */
    ToolDataSource addVoToEntity(ToolDataSourceAddVO reqVO);

    /**
     * Convert updateVo To entity
     *
     * @param reqVO 更新请求参数VO
     * @return ToolDataSource 数据源配置实体
     */
    ToolDataSource updateVoToEntity(ToolDataSourceUpdateVO reqVO);

    /**
     * Convert entity to DTO
     *
     * @param toolDataSource 数据源配置实体
     * @return ToolDataSourceDTO
     */
    ToolDataSourceDTO entityToDTO(ToolDataSource toolDataSource);

    /**
     * Convert entity to DTOList
     *
     * @param toolDataSourceList 数据源配置实体集合
     * @return List<ToolDataSourceDTO> ToolDataSourceDTO集合对象
     */
    List<ToolDataSourceDTO> entityToPage(List<ToolDataSource> toolDataSourceList);

    /**
     * Conver ToolDataSourceList to DTOList
     *
     * @param list 数据源集合
     * @return List<ToolDataSourceDTO>
     */
    List<ToolDataSourceDTO> toDTOList(List<ToolDataSource> list);
}