package com.wick.boot.module.okx.market.convert;

import com.wick.boot.module.okx.market.model.dto.MarketCoinAddVO;
import com.wick.boot.module.okx.market.model.entity.MarketCoin;
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

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数
     * @return
     */
    MarketCoin addVoToEntity(MarketCoinAddVO reqVO);
}
