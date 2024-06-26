package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.SystemLoginLogDTO;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统日志 Convert
 *
 * @author ZhangZiHeng
 * @date 2024-06-26
 */
@Mapper
public interface SystemLoggerConvert {

    SystemLoggerConvert INSTANCE = Mappers.getMapper(SystemLoggerConvert.class);

    /**
     * Convert entity to Dto
     *
     * @param records 登录日志集合
     * @return
     */
    List<SystemLoginLogDTO> entityToLoginLogDTOS(List<SystemLoginLog> records);

}
