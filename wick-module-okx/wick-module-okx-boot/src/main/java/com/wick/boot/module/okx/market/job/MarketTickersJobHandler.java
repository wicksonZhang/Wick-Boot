package com.wick.boot.module.okx.market.job;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.okx.api.market.ApiMarketCoin;
import com.wick.boot.module.okx.enums.market.MarketInstTypeEnum;
import com.wick.boot.module.okx.market.mapper.MarketTickersMapper;
import com.wick.boot.module.okx.market.model.dto.MarketCoinDTO;
import com.wick.boot.module.okx.market.model.entity.MarketTickers;
import com.wick.boot.module.okx.model.market.MarketTickersQueryVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 市场行情定时任务
 *
 * @author: Wickson
 * @create: 2024-11-21 22:03
 **/
@Slf4j
@Component
public class MarketTickersJobHandler {

    @Resource
    private MarketTickersMapper tickersMapper;

    @Resource
    private ApiMarketCoin apiMarketCoin;

    @XxlJob("marketTickersJobHandler")
    public void marketTickersJobHandler() {
        // 调用 API 获取数据
        MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
        queryVO.setInstType(MarketInstTypeEnum.SWAP.getDescription());
        ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);
        validateResponse(response);

        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
        List<MarketTickers> data = jsonObject.getJSONArray("data").toList(MarketTickers.class);
        tickersMapper.insertBatch(data);
    }

    private void validateResponse(ForestResponse<String> response) {
        int statusCode = response.statusCode();
        if (HttpStatus.HTTP_OK != statusCode) {
            log.error("市场行情调用失败！");
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

}
