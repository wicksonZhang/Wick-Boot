package com.wick.boot.module.system.convert;

import com.wick.boot.common.core.model.dto.OptionDTO;
import com.wick.boot.module.system.model.dto.dictdata.SystemDictDataDTO;
import com.wick.boot.module.system.model.entity.SystemDictData;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataAddVO;
import com.wick.boot.module.system.model.vo.dictdata.SystemDictDataUpdateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字典数据 Convert
 *
 * @author Wickson
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
    SystemDictData addVoToEntity(SystemDictDataAddVO reqVO);

    /**
     * Convert Vo to Entity
     *
     * @param reqVO 新增字典数据请求参数
     * @return 字典数据
     */
    SystemDictData updateVoToEntity(SystemDictDataUpdateVO reqVO);

    /**
     * Convert entity To DTO
     *
     * @param systemDictData 字典数据
     * @return SystemDictDataDTO 字典数据DTO
     */
    SystemDictDataDTO entityToDictDataDTO(SystemDictData systemDictData);

    /**
     * Convert entity To Option
     *
     * @param systemDictDataList 字典数据集合
     * @return List<SystemDictOptionsDTO>
     */
    List<OptionDTO<String>> entityToDictDataOptions(List<SystemDictData> systemDictDataList);

}
