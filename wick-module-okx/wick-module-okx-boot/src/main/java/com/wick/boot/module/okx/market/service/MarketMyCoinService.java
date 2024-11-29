package com.wick.boot.module.okx.market.service;

import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;

/**
 * 我的自选-应用服务类
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
public interface MarketMyCoinService {

    /**
     * 新增我的自选数据
     *
     * @param reqVO 新增请求参数
     */
    Long addMarketMyCoin(MarketMyCoinAddVO reqVO);

}