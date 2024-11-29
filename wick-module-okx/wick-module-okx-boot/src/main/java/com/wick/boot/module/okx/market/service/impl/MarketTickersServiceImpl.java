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
        String format = String.format("%02d", NumberUtil.parseInt(time));
        String accessKey = GlobalCacheConstants.getOkxMarketTickers(format + "_MIN:" + "*");
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
            String key = String.format("%02d", NumberUtil.parseInt(time)) + "_MIN:" + marketTickers.getInstId();
            String redisKey = GlobalCacheConstants.getOkxMarketTickers(key);
            redisService.setCacheObject(redisKey, marketTickers);
        }
    }

    @Override
    public void updateBatch(List<MarketTickers> updatedList, String time) {
        for (MarketTickers marketTickers : updatedList) {
            String key = String.format("%02d", NumberUtil.parseInt(time)) + "_MIN:" + marketTickers.getInstId();
            String redisKey = GlobalCacheConstants.getOkxMarketTickers(key);
            redisService.setCacheObject(redisKey, marketTickers);
        }
    }

    @Override
    public void deleteBatchIds(List<String> keys, String time) {
        String format = String.format("%02d", NumberUtil.parseInt(time));
        for (String key : keys) {
            String redisKey = GlobalCacheConstants.getOkxMarketTickers(format + "_MIN:" + key);
            redisService.deleteObject(redisKey);
        }
    }
}
