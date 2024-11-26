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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
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

    @Resource(name = "commonExecutor")
    private Executor executor;

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
        try {
            // 明确指定泛型类型
            List<CompletableFuture<List<MarketCoin>>> futures = Arrays.stream(MarketInstTypeEnum.values())
                    .map(typeEnum -> CompletableFuture.supplyAsync(() -> {
                        try {
                            log.info("开始获取 {} 类型的市场行情数据", typeEnum.getDescription());

                            MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
                            queryVO.setInstType(typeEnum.getDescription());

                            ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);
                            validateResponse(response);

                            JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
                            // 明确指定转换类型
                            List<MarketCoin> data = JSONUtil.toList(jsonObject.getJSONArray("data"), MarketCoin.class);

                            log.info("成功获取 {} 类型的市场行情数据，共计 {} 条记录。", typeEnum.getDescription(), data.size());
                            return data;
                        } catch (Exception e) {
                            log.error("获取{}类型市场行情数据失败: {}", typeEnum.getDescription(), e.getMessage(), e);
                            return Lists.<MarketCoin>newArrayList();
                        }
                    }, executor))
                    .collect(Collectors.toList());

            // 等待所有任务完成并收集结果
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // 收集结果
            for (CompletableFuture<List<MarketCoin>> future : futures) {
                try {
                    List<MarketCoin> data = future.get(5, TimeUnit.SECONDS);
                    if (CollectionUtil.isNotEmpty(data)) {
                        result.addAll(data);
                    }
                } catch (Exception e) {
                    log.error("处理市场行情数据失败: {}", e.getMessage(), e);
                }
            }

        } catch (Exception e) {
            log.error("获取市场行情数据过程中发生异常: {}", e.getMessage(), e);
        }

        return result;
    }

    /**
     * 处理市场行情数据，包含新增、更新和删除操作
     */
    private void handlerData(List<MarketCoin> originData, List<MarketCoin> remoteData) {
        // 将数据转换为 Map 结构，方便后续对比处理
        Map<String, MarketCoin> remoteMap = remoteData.stream()
                .collect(Collectors.toMap(MarketCoin::getInstId, data -> data, (k1, k2) -> k1));
        Map<String, MarketCoin> originMap = originData.stream()
                .collect(Collectors.toMap(MarketCoin::getInstId, data -> data, (k1, k2) -> k1));

        // 并行处理数据
        CompletableFuture<Void> insertFuture = CompletableFuture.runAsync(() -> insertData(originMap, remoteMap), executor);

        CompletableFuture<Void> updateFuture = CompletableFuture.runAsync(() -> updateData(originMap, remoteMap), executor);

        CompletableFuture<Void> deleteFuture = CompletableFuture.runAsync(() -> deleteData(originMap, remoteMap), executor);

        // 等待所有操作完成
        CompletableFuture.allOf(insertFuture, updateFuture, deleteFuture).join();
    }

    /**
     * 插入新增的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     */
    private void insertData(Map<String, MarketCoin> originMap, Map<String, MarketCoin> remoteMap) {
        try {
            List<String> insertCoin = CollectionUtil.subtractToList(remoteMap.keySet(), originMap.keySet());
            if (insertCoin.isEmpty()) {
                return;
            }

            List<MarketCoin> list = insertCoin.stream()
                    .map(remoteMap::get)
                    .collect(Collectors.toList());

            // 分批插入数据
            Lists.partition(list, 100).forEach(batch -> {
                try {
                    this.coinMapper.insertBatch(batch);
                } catch (Exception e) {
                    log.error("批量插入市场行情数据失败: {}", e.getMessage(), e);
                }
            });

            log.info("成功插入{}条市场行情数据", list.size());
        } catch (Exception e) {
            log.error("插入市场行情数据失败: {}", e.getMessage(), e);
        }
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
            MarketCoin marketCoin = originMap.get(instId);
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
