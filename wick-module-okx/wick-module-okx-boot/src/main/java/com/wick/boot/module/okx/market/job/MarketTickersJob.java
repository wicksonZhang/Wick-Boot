package com.wick.boot.module.okx.market.job;

import cn.hutool.core.collection.CollUtil;
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
import com.wick.boot.module.okx.market.model.dto.tickers.MarketTickersDTO;
import com.wick.boot.module.okx.market.model.entity.MarketTickers;
import com.wick.boot.module.okx.market.service.MarketTickersService;
import com.wick.boot.module.okx.market.utils.DingTalkUtil;
import com.wick.boot.module.okx.model.market.MarketTickersQueryVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 同步市场行情-定时任务
 *
 * @author Wickson
 * @date 2024-11-24 20:00:15
 */
@Slf4j
@Component
public class MarketTickersJob {

    @Resource
    private MarketTickersService tickersService;

    @Resource
    private ApiMarketCoin apiMarketCoin;

    @Resource
    private DingTalkUtil dingTalkUtil;

    @Resource(name = "commonExecutor")
    private Executor executor;

    /**
     * 每三分钟同步一次市场行情信息，并推送
     */
    @XxlJob("marketTickersByThreeMinutes")
    @Transactional(rollbackFor = Exception.class)
    public void marketTickersByThreeMinutes() {
        try {
            List<MarketTickers> originData = tickersService.selectList();
            List<MarketTickers> remoteData = this.getRemoteData();

            if (CollUtil.isEmpty(remoteData)) {
                log.warn("未获取到市场行情数据，本次同步终止");
                return;
            }

            // 使用并行处理提升性能
            this.handlerData(originData, remoteData);
            log.info("市场行情同步完成，处理数据量: {}", remoteData.size());
        } catch (Exception e) {
            log.error("市场行情同步失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 从远程服务获取市场行情数据
     *
     * @return 处理后的市场行情列表
     */
    private List<MarketTickers> getRemoteData() {
        try {
            // 明确指定泛型类型
            log.info("开始获取 合约 类型的市场行情数据");

            MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
            queryVO.setInstType(MarketInstTypeEnum.SWAP.getDescription());

            ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);
            validateResponse(response);

            JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
            // 明确指定转换类型
            List<MarketTickers> data = JSONUtil.toList(jsonObject.getJSONArray("data"), MarketTickers.class);

            log.info("成功获取合约类型的市场行情数据，共计 {} 条记录。", data.size());
            return data;
        } catch (Exception e) {
            log.error("获取合约类型市场行情数据失败: {}", e.getMessage());
        }
        return Lists.newArrayList();
    }

    /**
     * 处理市场行情数据，包含新增、更新和删除操作
     */
    private void handlerData(List<MarketTickers> originData, List<MarketTickers> remoteData) {
        // 使用 LinkedHashMap 保持插入顺序，提高查找效率
        Map<String, MarketTickers> remoteMap = remoteData.stream()
                .collect(Collectors.toMap(
                        MarketTickers::getInstId,
                        Function.identity()
                ));

        Map<String, MarketTickers> originMap = originData.stream()
                .collect(Collectors.toMap(
                        MarketTickers::getInstId,
                        Function.identity()
                ));

        // 并行处理三个操作
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> insertData(originMap, remoteMap), executor),
                CompletableFuture.runAsync(() -> updateData(originMap, remoteMap), executor),
                CompletableFuture.runAsync(() -> deleteData(originMap, remoteMap), executor)
        ).join();
    }

    /**
     * 插入新增的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     */
    private void insertData(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap) {
        try {
            List<String> insertCoin = CollectionUtil.subtractToList(remoteMap.keySet(), originMap.keySet());
            if (CollUtil.isEmpty(insertCoin)) {
                return;
            }

            List<MarketTickers> list = insertCoin.stream()
                    .map(remoteMap::get)
                    .collect(Collectors.toList());

            // 判断是否存在上新币种，如果存在，则需要推送到钉钉
            if (CollectionUtil.isNotEmpty(list)) {
                List<MarketTickersDTO> resultList = Lists.newArrayList();
                list.forEach(marketTickers -> {
                    MarketTickersDTO tickersDTO = new MarketTickersDTO();
                    tickersDTO.setInstId(marketTickers.getInstId().replace("-SWAP", ""));
                    tickersDTO.setThreeChangePercent("0.00%");
                    tickersDTO.setThreeChangePercent("0.00%");
                    resultList.add(tickersDTO);
                });
                dingTalkUtil.sendMarketTickers("上新币种：", resultList);
            }

            // 分批插入数据
            this.tickersService.insertBatch(list);
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
    private void updateData(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap) {
        // 获取本地和远程数据中共有的币种（交集）
        Set<String> updateCoin = CollectionUtil.intersectionDistinct(remoteMap.keySet(), originMap.keySet());
        if (CollUtil.isEmpty(updateCoin)) {
            return;
        }

        // 获取交集的本地数据列表
        List<MarketTickers> updatedList = updateCoin.stream().map(remoteMap::get).collect(Collectors.toList());

        // 计算涨跌幅并筛选出需要推送的数据
        List<MarketTickersDTO> tickersDTOS = calculateChangePercent(originMap, remoteMap);
        if (CollectionUtil.isNotEmpty(tickersDTOS)) {
            dingTalkUtil.sendMarketTickers("财富密码", tickersDTOS);
        }

        // 更新交集数据到数据库
        this.tickersService.updateBatch(updatedList);
    }

    private List<MarketTickersDTO> calculateChangePercent(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap) {
        // 过滤掉不符合条件的数据, 按 threeChangePercent 绝对值降序
        List<MarketTickersDTO> collect = new ArrayList<>();
        for (MarketTickers local : originMap.values()) {
            MarketTickers remoteTickers = remoteMap.get(local.getInstId());
            MarketTickersDTO tickerDTO = createTickerDTO(local, remoteTickers);
            if (tickerDTO != null) {
                collect.add(tickerDTO);
            }
        }
        collect.sort(Comparator.comparingDouble(dto -> -Math.abs(parseDoubleSafely(dto.getThreeChangePercent()))));
        return collect;
    }

    /**
     * 优化后的 DTO 创建方法
     * 添加数值校验和异常处理
     */
    private MarketTickersDTO createTickerDTO(MarketTickers origin, MarketTickers remote) {
        try {
            double originLast = parseDoubleSafely(origin.getLast());
            double remoteLast = parseDoubleSafely(remote.getLast());

            // 避免除数为0
            if (Math.abs(originLast) < 1e-10) {
                log.warn("本地数据 last 值接近0，跳过计算: {}", origin.getInstId());
                return null;
            }

            // 涨跌幅计算方式：(新价格 - 旧价格) / 旧价格 * 100
            double threePercent = ((remoteLast - originLast) / originLast) * 100;

            // 使用常量定义阈值
            final double CHANGE_THRESHOLD = 3.0;
            if (Math.abs(threePercent) >= CHANGE_THRESHOLD) {
                double dayStartPrice = parseDoubleSafely(remote.getSodUtc8());
                // 日内涨跌幅也使用起始价格作为基数
                double dayPercent = ((remoteLast - dayStartPrice) / dayStartPrice) * 100;

                return MarketTickersDTO.builder()
                        .instId(remote.getInstId().replace("-SWAP", ""))
                        .threeChangePercent(String.format("%.2f%%", threePercent))
                        .dayChangePercent(String.format("%.2f%%", dayPercent))
                        .build();
            }
        } catch (Exception e) {
            log.warn("计算涨跌幅失败: instId={}, error={}", origin.getInstId(), e.getMessage());
        }
        return null;
    }

    private double parseDoubleSafely(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * 删除多余的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     */
    private void deleteData(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap) {
        // 获取本地数据中远程不存在的币种（多余）
        List<String> deleteData = CollectionUtil.subtractToList(originMap.keySet(), remoteMap.keySet());
        if (deleteData.isEmpty()) {
            return;
        }

        // 从数据库中删除多余的数据
        this.tickersService.deleteBatchIds(deleteData);
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
