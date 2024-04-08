package cn.wickson.security.system.convert;

import cn.wickson.security.system.model.dto.SystemDictDataDTO;
import cn.wickson.security.system.model.dto.SystemDictTypeDTO;
import cn.wickson.security.system.model.entity.SystemDictData;
import cn.wickson.security.system.model.entity.SystemDictType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SystemDictConvert {

    SystemDictConvert INSTANCE = Mappers.getMapper(SystemDictConvert.class);

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
}
