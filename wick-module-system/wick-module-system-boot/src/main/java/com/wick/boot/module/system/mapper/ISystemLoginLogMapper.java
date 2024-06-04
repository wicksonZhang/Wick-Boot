package com.wick.boot.module.system.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.system.model.entity.SystemLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统给登录日志Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-06-04
 */
@Mapper
public interface ISystemLoginLogMapper extends BaseMapperX<SystemLoginLog> {
}
