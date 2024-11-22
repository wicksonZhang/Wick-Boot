package com.wick.boot.module.okx.market.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 币种信息管理-转换类
 *
 * @author Wickson
 * @date 2024-11-21 23:35
 */
@Mapper
public interface MarketCoinConvert {

    MarketCoinConvert INSTANCE = Mappers.getMapper(MarketCoinConvert.class);
}
