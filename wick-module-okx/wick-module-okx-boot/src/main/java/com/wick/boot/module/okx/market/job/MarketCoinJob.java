package com.wick.boot.module.okx.market.job;

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
 * 市场行情定时任务
 *
 * @author: Wickson
 * @create: 2024-11-21 22:03
 **/
@Slf4j
@Component
public class MarketCoinJob {

    @Resource
    private MarketCoinMapper coinMapper;

    @Resource
    private ApiMarketCoin apiMarketCoin;

    /**
     * 每五分钟同步一次市场行情信息,并推送
     */
    @XxlJob("marketTickersByFiveMinutes")
    @Transactional(rollbackFor = Exception.class)
    public void marketTickersByFiveMinutes() {
        try {
            // 获取本地数据
            List<MarketCoin> originData = this.coinMapper.selectList(null);

            // 获取远程数据
            List<MarketCoin> remoteData = this.getRemoteData();
            if (remoteData.isEmpty()) {
                log.warn("未获取到市场行情数据，无需更新！");
                return;
            }

            // 数据处理
            this.handlerData(originData, remoteData);

            // 数据推送
            this.pushData(originData, remoteData);
            log.info("成功同步市场行情数据，共计 {} 条记录。", remoteData.size());
        } catch (Exception e) {
            log.error("同步市场行情数据失败：{}", e.getMessage(), e);
            throw e; // 确保事务回滚
        }
    }

    /**
     * 从远程服务获取市场行情数据
     *
     * @return 处理后的市场行情列表
     */
    private List<MarketCoin> getRemoteData() {
        List<MarketCoin> result = Lists.newArrayList();
        for (MarketInstTypeEnum typeEnum : MarketInstTypeEnum.values()) {
            // 请求参数
            MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
            queryVO.setInstType(typeEnum.getDescription());

            // 发送数据请求
            ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);
            validateResponse(response);

            // 转换结果
            JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
            List<MarketCoin> data = jsonObject.getJSONArray("data").toList(MarketCoin.class);
            result.addAll(data);
            log.info("成功获取 {} 类型的市场行情数据，共计 {} 条记录。", typeEnum.getDescription(), data.size());
        }
        return result;
    }

    private void handlerData(List<MarketCoin> originData, List<MarketCoin> remoteData) {
        // 处理数据
//        insertData(originData, remoteData);
//        updateData(originData, remoteData);
//        deleteData(originData, remoteData);
    }

    /**
     * 推送数据到 dingTalk
     *
     * @param originData
     * @param remoteData
     */
    private void pushData(List<MarketCoin> originData, List<MarketCoin> remoteData) {

    }

    /**
     * 校验远程服务响应
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
