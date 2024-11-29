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
    List<MarketTickers> selectList(String time);

    /**
     * 批量插入数据
     *
     * @param insertList  市场行情数据
     * @param executeTime 执行时间
     */
    void insertBatch(List<MarketTickers> insertList, String executeTime);

    /**
     * 批量更新数据
     *
     * @param updatedList 市场行情数据
     * @param executeTime 执行时间
     */
    void updateBatch(List<MarketTickers> updatedList, String executeTime);

    /**
     * 批量删除数据
     *
     * @param keys        产品名称
     * @param executeTime 执行时间
     */
    void deleteBatchIds(List<String> keys, String executeTime);
}
