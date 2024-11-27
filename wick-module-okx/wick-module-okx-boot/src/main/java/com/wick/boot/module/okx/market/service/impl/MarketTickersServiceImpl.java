package com.wick.boot.module.okx.market.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.constant.GlobalCacheConstants;
import com.wick.boot.common.redis.service.RedisService;
import com.wick.boot.module.okx.market.model.entity.MarketTickers;
import com.wick.boot.module.okx.market.service.MarketTickersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 市场行情-业务实现层
 *
 * @author Wickson
 * @date 2024-11-27
 */
@Slf4j
@Service
public class MarketTickersServiceImpl implements MarketTickersService {

    @Resource
    private RedisService redisService;

    @Override
    public List<MarketTickers> selectList() {
        // 获取所有的 key
        Collection<String> keys = redisService.keys(GlobalCacheConstants.getOkxMarketTickers("*"));
        if (CollUtil.isEmpty(keys)) {
            return Lists.newArrayList();
        }
        List<MarketTickers> marketTickerList = Lists.newArrayList();
        for (String key : keys) {
            MarketTickers marketTickers = redisService.getCacheObject(key);
            marketTickerList.add(marketTickers);
        }
        return marketTickerList;
    }

    @Override
    public void insertBatch(List<MarketTickers> insertList) {
        for (MarketTickers marketTickers : insertList) {
            String key = GlobalCacheConstants.getOkxMarketTickers(marketTickers.getInstId());
            redisService.setCacheObject(key, marketTickers);
        }
    }

    @Override
    public void updateBatch(List<MarketTickers> updatedList) {
        for (MarketTickers marketTickers : updatedList) {
            String key = GlobalCacheConstants.getOkxMarketTickers(marketTickers.getInstId());
            redisService.setCacheObject(key, marketTickers);
        }
    }

    @Override
    public void deleteBatchIds(List<String> keys) {
        for (String key : keys) {
            redisService.deleteObject(key);
        }
    }
}
