package com.wick.boot.module.okx.market.service;

import com.wick.boot.module.okx.market.mapper.MarketTickersMapper;

import javax.annotation.Resource;

/**
 * 币种信息-防腐层
 *
 * @author: Wickson
 * @create: 2024-11-21 23:27
 **/
public abstract class AbstractMarketCoinService {

    @Resource
    protected MarketTickersMapper marketTickersMapper;

}
