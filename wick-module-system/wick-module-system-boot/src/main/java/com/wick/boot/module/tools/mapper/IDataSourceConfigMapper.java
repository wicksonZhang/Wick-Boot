package com.wick.boot.module.tools.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.tools.model.entity.DataSourceConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author ZhangZiHeng
 * @date 2024-07-11
 */
@Mapper
public interface IDataSourceConfigMapper extends BaseMapperX<DataSourceConfig> {

}
