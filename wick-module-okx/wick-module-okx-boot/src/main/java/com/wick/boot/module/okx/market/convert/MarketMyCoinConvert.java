package com.wick.boot.module.okx.market.convert;

import com.wick.boot.module.okx.market.model.entity.MarketMyCoin;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 我的自选管理-转换类
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
@Mapper
public interface MarketMyCoinConvert {

    MarketMyCoinConvert INSTANCE = Mappers.getMapper(MarketMyCoinConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return MarketMyCoin 我的自选
     */
    MarketMyCoin addVoToEntity(MarketMyCoinAddVO reqVO);

}