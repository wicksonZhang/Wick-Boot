package com.wick.boot.module.okx.market.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
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
import java.util.concurrent.TimeUnit;

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
    public List<MarketTickers> selectList(String time) {
        // 获取所有的 key
        String accessKey = GlobalCacheConstants.getOkxMarketTickers(time + "_MIN:" + "*");
        Collection<String> keys = redisService.keys(accessKey);
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
    public void insertBatch(List<MarketTickers> insertList, String time) {
        for (MarketTickers marketTickers : insertList) {
            long number = NumberUtil.parseLong(time);
            String key = time + "_MIN:" + marketTickers.getInstId();
            String redisKey = GlobalCacheConstants.getOkxMarketTickers(key);
            redisService.setCacheObject(redisKey, marketTickers, number + 1, TimeUnit.MINUTES);
        }
    }

    @Override
    public void updateBatch(List<MarketTickers> updatedList, String time) {
        for (MarketTickers marketTickers : updatedList) {
            long number = NumberUtil.parseLong(time);
            String key = time + "_MIN:" + marketTickers.getInstId();
            String redisKey = GlobalCacheConstants.getOkxMarketTickers(key);
            redisService.setCacheObject(redisKey, marketTickers, number + 1, TimeUnit.MINUTES);
        }
    }

    @Override
    public void deleteBatchIds(List<String> keys, String time) {
        for (String key : keys) {
            String redisKey = GlobalCacheConstants.getOkxMarketTickers(time + "_MIN:" + key);
            redisService.deleteObject(redisKey);
        }
    }
}
