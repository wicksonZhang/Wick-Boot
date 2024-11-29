package com.wick.boot.module.okx.market.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.entity.MarketCoin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 市场行情管理-Mapper接口
 *
 * @date 2024-11-28 15:34
 */
@Mapper
public interface MarketCoinMapper extends BaseMapperX<MarketCoin> {

}
