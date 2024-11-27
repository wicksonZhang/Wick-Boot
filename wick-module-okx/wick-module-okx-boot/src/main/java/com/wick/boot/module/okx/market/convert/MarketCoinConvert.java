package com.wick.boot.module.okx.market.convert;

import com.wick.boot.module.okx.market.model.dto.allcoin.MarketAllCoinDTO;
import com.wick.boot.module.okx.market.model.entity.MarketTickers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 币种信息管理-转换类
 *
 * @author Wickson
 * @date 2024-11-21 23:35
 */
@Mapper
public interface MarketCoinConvert {

    MarketCoinConvert INSTANCE = Mappers.getMapper(MarketCoinConvert.class);

    List<MarketAllCoinDTO> entityToPage(List<MarketTickers> records);
}
