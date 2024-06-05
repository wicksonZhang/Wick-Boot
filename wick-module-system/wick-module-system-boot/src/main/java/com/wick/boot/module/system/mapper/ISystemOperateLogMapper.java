package com.wick.boot.module.system.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemOperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统操作日志Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@Mapper
public interface ISystemOperateLogMapper extends BaseMapperX<SystemOperateLog> {
}
