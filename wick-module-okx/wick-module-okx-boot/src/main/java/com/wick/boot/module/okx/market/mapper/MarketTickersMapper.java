package com.wick.boot.module.okx.market.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.entity.MarketTickers;
import org.apache.ibatis.annotations.Mapper;


/**
 * 市场行情管理-Mapper接口
 *
 * @author Lenovo
 * @date 2024-11-21 22:00
 */
@Mapper
public interface MarketTickersMapper extends BaseMapperX<MarketTickers> {

}
