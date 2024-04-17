package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemDictDataDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.vo.dict.data.AddDictDataReqVO;
import com.wick.boot.module.system.model.vo.dict.data.UpdateDictDataReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据 Convert
 *
 * @author ZhangZiHeng
 * @date 2024-04-17
 */
@Mapper
public interface SystemDictDataConvert {

    SystemDictDataConvert INSTANCE = Mappers.getMapper(SystemDictDataConvert.class);

    /**
     * Convert Vo to Entity
     *
     * @param reqVO 新增字典数据请求参数
     * @return 字典数据
     */
    @Mappings({
            @Mapping(target = "label", source = "name"),
            @Mapping(target = "dictType", source = "typeCode")
    })
    SystemDictData addVoToEntity(AddDictDataReqVO reqVO);

    /**
     * Convert Vo to Entity
     *
     * @param reqVO 新增字典数据请求参数
     * @return 字典数据
     */
    @Mappings({
            @Mapping(target = "label", source = "name"),
            @Mapping(target = "dictType", source = "typeCode")
    })
    SystemDictData updateVoToEntity(UpdateDictDataReqVO reqVO);

    /**
     * Convert entity To DTO
     *
     * @param systemDictData 字典数据
     * @return SystemDictDataDTO 字典数据DTO
     */
    @Mappings({
            @Mapping(target = "name", source = "label"),
            @Mapping(target = "typeCode", source = "dictType")
    })
    SystemDictDataDTO entityToDictDataDTO(SystemDictData systemDictData);
}
