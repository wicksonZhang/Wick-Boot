package com.wick.boot.module.okx.market.service;

import com.wick.boot.module.okx.market.model.entity.MarketTickers;

import java.util.List;

/**
 * 市场行情-业务层
 *
 * @author Wickson
 * @date 2024-11-27
 */
public interface MarketTickersService {

    /**
     * 获取市场行情数据
     *
     * @return
     */
    List<MarketTickers> selectList();

    /**
     * 批量插入数据
     *
     * @param insertList 市场行情数据
     */
    void insertBatch(List<MarketTickers> insertList);

    /**
     * 批量更新数据
     *
     * @param updatedList 市场行情数据
     */
    void updateBatch(List<MarketTickers> updatedList);

    /**
     * 批量删除数据
     *
     * @param keys 产品名称
     */
    void deleteBatchIds(List<String> keys);
}
