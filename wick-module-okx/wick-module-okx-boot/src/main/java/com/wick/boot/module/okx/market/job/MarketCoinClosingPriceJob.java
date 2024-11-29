package com.wick.boot.module.okx.market.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.google.common.collect.Lists;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.okx.api.market.ApiMarketCoin;
import com.wick.boot.module.okx.enums.market.MarketInstTypeEnum;
import com.wick.boot.module.okx.market.mapper.MarketCoinMapper;
import com.wick.boot.module.okx.market.model.entity.MarketCoin;
import com.wick.boot.module.okx.model.market.MarketTickersQueryVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 币种收盘价(UTC+8)-定时任务
 *
 * @author Wickson
 * @date 2024-11-28
 */
@Slf4j
@Component
public class MarketCoinClosingPriceJob {

    @Resource
    private ApiMarketCoin apiMarketCoin;

    @Resource
    private MarketCoinMapper coinMapper;

    @XxlJob("marketCoinClosingPricesBy8AMJob")
    @Transactional(rollbackFor = Exception.class)
    public void marketCoinClosingPricesBy8AMJob() {
        try {
            log.info("币种收盘价(UTC+8)-定时任务");
            List<MarketCoin> remoteData = getRemoteData();
            if (CollUtil.isEmpty(remoteData)) {
                log.warn("未获取到市场行情数据，本次同步终止");
                return;
            }

            // 保存到数据库
            coinMapper.insertBatch(remoteData);
        } catch (Exception e) {
            log.error("市场行情同步失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    private List<MarketCoin> getRemoteData() {
        try {
            // 明确指定泛型类型
            MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
            queryVO.setInstType(MarketInstTypeEnum.SWAP.getDescription());
            ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);
            validateResponse(response);

            JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
            // 明确指定转换类型
            List<MarketCoin> data = JSONUtil.toList(jsonObject.getJSONArray("data"), MarketCoin.class);
            log.info("成功获取合约类型的市场行情数据，共计 {} 条记录。", data.size());
            return data;
        } catch (Exception e) {
            log.error("获取合约类型市场行情数据失败: {}", e.getMessage());
        }
        return Lists.newArrayList();
    }

    /**
     * 校验远程服务响应数据是否正常
     *
     * @param response 服务返回的响应数据
     */
    private void validateResponse(ForestResponse<String> response) {
        int statusCode = response.statusCode();
        if (HttpStatus.HTTP_OK != statusCode) {
            log.error("市场行情接口调用失败，状态码：{}", statusCode);
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
        log.debug("市场行情接口调用成功，响应数据：{}", response.getContent());
    }

}
