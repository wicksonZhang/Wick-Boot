package com.wick.boot.module.okx.market.job;

import cn.hutool.core.collection.CollectionUtil;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 同步市场行情-定时任务
 *
 * @author Wickson
 * @date 2024-11-24 20:00:15
 */
@Slf4j
@Component
public class MarketCoinJob {

    @Resource
    private MarketCoinMapper coinMapper;

    @Resource
    private ApiMarketCoin apiMarketCoin;

    /**
     * 每五分钟同步一次市场行情信息，并推送
     */
    @XxlJob("marketTickersByFiveMinutes")
    @Transactional(rollbackFor = Exception.class)
    public void marketTickersByFiveMinutes() {
        try {
            // 获取本地数据，作为基准数据
            List<MarketCoin> originData = this.coinMapper.selectList();

            // 从远程服务获取最新的市场行情数据
            List<MarketCoin> remoteData = this.getRemoteData();
            if (remoteData.isEmpty()) {
                log.warn("未获取到市场行情数据，无需更新！");
                return;
            }

            // 对本地数据与远程数据进行处理，更新数据库
            this.handlerData(originData, remoteData);
            log.info("成功同步市场行情数据，共计 {} 条记录。", remoteData.size());
        } catch (Exception e) {
            log.error("同步市场行情数据失败：{}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 从远程服务获取市场行情数据
     *
     * @return 处理后的市场行情列表
     */
    private List<MarketCoin> getRemoteData() {
        List<MarketCoin> result = Lists.newArrayList();
        // 遍历所有市场类型，获取各自的数据
        for (MarketInstTypeEnum typeEnum : MarketInstTypeEnum.values()) {
            // 构造请求参数
            MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
            queryVO.setInstType(typeEnum.getDescription());

            // 发送请求到远程服务
            ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);
            validateResponse(response);

            // 解析远程服务返回的数据
            JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
            List<MarketCoin> data = jsonObject.getJSONArray("data").toList(MarketCoin.class);
            result.addAll(data);

            log.info("成功获取 {} 类型的市场行情数据，共计 {} 条记录。", typeEnum.getDescription(), data.size());
        }
        return result;
    }

    /**
     * 处理市场行情数据，包含新增、更新和删除操作
     *
     * @param originData 本地数据
     * @param remoteData 远程数据
     */
    private void handlerData(List<MarketCoin> originData, List<MarketCoin> remoteData) {
        // 将数据转换为 Map 结构，方便后续对比处理
        Map<String, MarketCoin> remoteMap = remoteData.stream().collect(Collectors.toMap(MarketCoin::getInstId, data -> data));
        Map<String, MarketCoin> originMap = originData.stream().collect(Collectors.toMap(MarketCoin::getInstId, data -> data));

        // 新增数据，并推送
        insertData(originMap, remoteMap);
        // 更新已有的数据
        updateData(originMap, remoteMap);
        // 删除本地多余的数据
        deleteData(originMap, remoteMap);
    }

    /**
     * 插入新增的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     */
    private void insertData(Map<String, MarketCoin> originMap, Map<String, MarketCoin> remoteMap) {
        // 获取远程数据中本地不存在的币种（新增）
        List<String> insertCoin = CollectionUtil.subtractToList(remoteMap.keySet(), originMap.keySet());
        if (insertCoin.isEmpty()) {
            return;
        }

        // 将新增的数据插入数据库
        List<MarketCoin> list = Lists.newArrayList();
        for (String instId : insertCoin) {
            MarketCoin marketCoin = remoteMap.get(instId);
            list.add(marketCoin);
        }
        this.coinMapper.insertBatch(list);
    }

    /**
     * 更新已有的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     */
    private void updateData(Map<String, MarketCoin> originMap, Map<String, MarketCoin> remoteMap) {
        // 获取本地和远程数据中共有的币种（交集）
        Set<String> updateCoin = CollectionUtil.intersectionDistinct(remoteMap.keySet(), originMap.keySet());
        if (updateCoin.isEmpty()) {
            return;
        }

        // 更新交集数据到数据库
        List<MarketCoin> list = Lists.newArrayList();
        for (String instId : updateCoin) {
            MarketCoin marketCoin = remoteMap.get(instId);
            list.add(marketCoin);
        }
        this.coinMapper.updateBatch(list);
    }

    /**
     * 删除多余的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     */
    private void deleteData(Map<String, MarketCoin> originMap, Map<String, MarketCoin> remoteMap) {
        // 获取本地数据中远程不存在的币种（多余）
        List<String> deleteData = CollectionUtil.subtractToList(originMap.keySet(), remoteMap.keySet());
        if (deleteData.isEmpty()) {
            return;
        }

        // 从数据库中删除多余的数据
        List<MarketCoin> list = Lists.newArrayList();
        for (String instId : deleteData) {
            MarketCoin marketCoin = originMap.get(instId);
            list.add(marketCoin);
        }
        this.coinMapper.deleteBatchIds(list);
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
