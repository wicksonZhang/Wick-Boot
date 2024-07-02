package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.dict.data.SystemDictDataDTO;
import com.wick.boot.module.system.model.dto.dict.type.SystemDictTypeDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.entity.SystemDictType;
import com.wick.boot.module.system.model.vo.dict.type.AddDictTypeReqVO;
import com.wick.boot.module.system.model.vo.dict.type.UpdateDictTypeReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SystemDictTypeConvert {

    SystemDictTypeConvert INSTANCE = Mappers.getMapper(SystemDictTypeConvert.class);

    /**
     * DictTypeConvert addVO To Entity
     *
     * @param reqVO 新增请求参数
     * @return SystemDictType
     */
    SystemDictType addVoToEntity(AddDictTypeReqVO reqVO);

    /**
     * DictTypeConvert updateVO To Entity
     *
     * @param reqVO 新增请求参数
     * @return SystemDictType
     */
    SystemDictType updateVoToEntity(UpdateDictTypeReqVO reqVO);

    /**
     * DictTypeConvert Entity To List<DTO>
     *
     * @param dictTypeList 字典类型集合
     * @return List<SystemDictTypeDTO>
     */
    List<SystemDictTypeDTO> entityToDictTypeDTOS(List<SystemDictType> dictTypeList);

    /**
     * DictDataConvert Entity To List<DTO>
     *
     * @param dictDataList 字典数据集合
     * @return List<SystemDictDataDTO>
     */
    List<SystemDictDataDTO> entityToDictDataDTOS(List<SystemDictData> dictDataList);

    /**
     * DictDataConvert Entity To DTO
     *
     * @param systemDictData 字典数据
     * @return SystemDictDataDTO
     */
    @Mapping(target = "name", source = "label")
    SystemDictDataDTO entityToDictData(SystemDictData systemDictData);

    /**
     * Convert entity To DTO
     *
     * @param systemDictType 字典类型
     * @return SystemDictTypeDTO
     */
    SystemDictTypeDTO entityToDictTypeDTO(SystemDictType systemDictType);
}
