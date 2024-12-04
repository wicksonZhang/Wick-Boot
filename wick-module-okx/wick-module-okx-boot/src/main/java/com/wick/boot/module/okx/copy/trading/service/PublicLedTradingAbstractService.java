package com.wick.boot.module.okx.copy.trading.service;

import com.wick.boot.module.okx.copy.trading.mapper.PublicLedTradingMapper;

import javax.annotation.Resource;

/**
 * 交易员排名管理-防腐层
 *
 * @author Wickson
 * @date 2024-12-04 10:30
 */
public abstract class PublicLedTradingAbstractService {

    @Resource
    private PublicLedTradingMapper publicLedTradingMapper;

}