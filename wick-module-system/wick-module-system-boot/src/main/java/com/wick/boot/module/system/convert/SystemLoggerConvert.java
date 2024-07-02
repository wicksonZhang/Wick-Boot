package com.wick.boot.module.system.convert;

import com.wick.boot.module.system.model.dto.logger.login.SystemLoginLogDTO;
import com.wick.boot.module.system.model.dto.logger.operate.SystemOperateLogDTO;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
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

    /**
     * Convert entity to Dto
     *
     * @param records 操作日志集合
     * @return
     */
    List<SystemOperateLogDTO> entityToOperateLogDTOS(List<SystemOperateLog> records);
}
