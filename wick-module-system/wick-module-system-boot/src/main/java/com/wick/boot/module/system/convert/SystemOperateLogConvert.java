package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.OperateLogCreateReqDTO;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ZhangZiHeng
 * @date 2024-06-13
 */
@Mapper
public interface SystemOperateLogConvert {

    SystemOperateLogConvert INSTANCE = Mappers.getMapper(SystemOperateLogConvert.class);

    SystemOperateLog convert(OperateLogCreateReqDTO createReqDTO);
}
