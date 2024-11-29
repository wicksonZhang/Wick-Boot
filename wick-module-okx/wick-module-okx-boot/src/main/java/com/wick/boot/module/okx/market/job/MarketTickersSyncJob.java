package com.wick.boot.module.okx.market.job;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
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
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
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
@RequiredArgsConstructor
public class MarketTickersSyncJob {

    private final MarketTickersService tickersService;

    private final ApiMarketCoin apiMarketCoin;

    private final DingTalkUtil dingTalkUtil;

    @Resource(name = "commonExecutor")
    private Executor executor;

    /**
     * 每三分钟同步一次市场行情信息，并推送
     */
    @XxlJob("marketTickersSyncData")
    @Transactional(rollbackFor = Exception.class)
    public void execute() {
        try {
            // Step-1：校验并提取XXL-Job的参数
            String[] params = validateAndExtractParams(XxlJobHelper.getJobParam());

            // Step-2: 获取远程市场行情数据
            List<MarketTickers> remoteData = this.fetchRemoteData();
            if (CollUtil.isEmpty(remoteData)) {
                log.warn("未获取到市场行情数据，本次同步终止");
                return;
            }

            // Step-3: 获取本地市场行情数据
            List<MarketTickers> originData = tickersService.selectList(params[0]);

            // Step-4: 处理市场行情数据：插入、更新、删除操作
            this.processMarketData(originData, remoteData, params);

            log.info("市场行情同步完成，处理数据量: {}", remoteData.size());
        } catch (Exception e) {
            log.error("市场行情同步失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 校验XXL-Job参数并提取有效数据
     *
     * @param result xxl-job参数
     * @return 校验后的参数
     */
    private static String[] validateAndExtractParams(String result) {
        Asserts.check(StrUtil.isAllNotBlank(result), "xxl-job参数不能为空");
        String[] params = result.split(",");
        Asserts.check(NumberUtil.isNumber(params[0]), "xxl-job参数不正确");
        Asserts.check(NumberUtil.isDouble(params[1]), "xxl-job参数不正确");
        return params;
    }

    /**
     * 从远程API获取市场行情数据
     *
     * @return 处理后的市场行情列表
     */
    private List<MarketTickers> fetchRemoteData() {
        try {
            log.info("开始获取市场行情数据");

            // 构造查询条件，获取合约类型的市场行情
            MarketTickersQueryVO queryVO = new MarketTickersQueryVO();
            queryVO.setInstType(MarketInstTypeEnum.SWAP.getDescription());
            ForestResponse<String> response = apiMarketCoin.getTickers(queryVO);

            // 校验远程响应
            validateResponse(response);

            // 解析响应数据并转换为MarketTickers对象列表
            JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
            return JSONUtil.toList(jsonObject.getJSONArray("data"), MarketTickers.class);
        } catch (Exception e) {
            log.error("获取合约类型市场行情数据失败: {}", e.getMessage());
        }
        return Lists.newArrayList();
    }

    /**
     * 处理市场行情数据：插入、更新、删除操作
     */
    private void processMarketData(List<MarketTickers> originData, List<MarketTickers> remoteData, String[] params) {
        // 将市场行情数据按InstId映射为Map，方便后续操作
        Map<String, MarketTickers> originMap = mapByInstId(originData);
        Map<String, MarketTickers> remoteMap = mapByInstId(remoteData);

        // 并行执行插入、更新、删除操作
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> insertNewData(originMap, remoteMap, params), executor),
                CompletableFuture.runAsync(() -> updateExistingData(originMap, remoteMap, params), executor),
                CompletableFuture.runAsync(() -> deleteData(originMap, remoteMap, params), executor)
        ).join();
    }

    private Map<String, MarketTickers> mapByInstId(List<MarketTickers> data) {
        return data.stream().collect(Collectors.toMap(MarketTickers::getInstId, Function.identity()));
    }

    /**
     * 插入新增的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     * @param params    xxl-job 参数
     */
    private void insertNewData(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap, String[] params) {
        // 获取新增加的币种
        List<String> insertCoin = CollectionUtil.subtractToList(remoteMap.keySet(), originMap.keySet());
        if (CollUtil.isEmpty(insertCoin)) {
            return;
        }

        // 从远程数据中提取新增币种的MarketTickers对象
        List<MarketTickers> newData = insertCoin.stream().map(remoteMap::get).collect(Collectors.toList());

        // 推送新增币种的通知（例如钉钉通知）
        notifyNewCoins(newData);

        // 批量插入新增的市场行情数据
        tickersService.insertBatch(newData, params[0]);
        log.info("成功插入{}条市场行情数据", newData.size());
    }

    /**
     * 推送新增币种的通知
     *
     * @param newData 新增的币种数据
     */
    private void notifyNewCoins(List<MarketTickers> newData) {
        List<MarketTickersDTO> newCoins = BeanUtil.copyToList(newData, MarketTickersDTO.class);
        if (!newCoins.isEmpty()) {
            dingTalkUtil.sendMarketTickers(null, "上新币种：", newCoins);
        }
    }

    /**
     * 更新已有的市场行情数据
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     * @param params    xxl-job 参数
     */
    private void updateExistingData(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap, String[] params) {
        // 获取本地和远程数据中共有的币种（交集）
        Set<String> updateCoin = CollectionUtil.intersectionDistinct(remoteMap.keySet(), originMap.keySet());
        if (CollUtil.isEmpty(updateCoin)) {
            return;
        }

        // 计算涨跌幅并筛选出需要推送的数据
        double threshold = NumberUtil.parseDouble(params[1]);
        List<MarketTickersDTO> tickersDTOS = calculateChangePercent(originMap, remoteMap, threshold);
        if (CollectionUtil.isNotEmpty(tickersDTOS)) {
            // 推送钉钉通知
            dingTalkUtil.sendMarketTickers(params[1], "财富密码", tickersDTOS);
        }

        // 获取需要更新的MarketTickers对象列表
        List<MarketTickers> updatedList = updateCoin.stream().map(remoteMap::get).collect(Collectors.toList());

        // 批量更新数据到数据库
        tickersService.updateBatch(updatedList, params[0]);
    }

    private List<MarketTickersDTO> calculateChangePercent(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap, double threshold) {
        return originMap.values().stream()
                .filter(local -> remoteMap.containsKey(local.getInstId()))
                .map(local -> createTickerDTO(local, remoteMap.get(local.getInstId()), threshold))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(dto -> -Math.abs(Double.parseDouble(dto.getThreeChangePercent()))))
                .collect(Collectors.toList());
    }

    /**
     * 优化后的 DTO 创建方法
     * 添加数值校验和异常处理
     */
    private MarketTickersDTO createTickerDTO(MarketTickers origin, MarketTickers remote, double threshold) {
        try {
            double originLast = Double.parseDouble(origin.getLast());
            double remoteLast = Double.parseDouble(remote.getLast());

            // 如果原始数据价格接近0，跳过计算
            if (Math.abs(originLast) < 1e-10) {
                return null;
            }

            // 涨跌幅计算方式：(新价格 - 旧价格) / 旧价格 * 100
            double threePercent = ((remoteLast - originLast) / originLast) * 100;
            if (Math.abs(threePercent) >= threshold) {
                double dayStartPrice = Double.parseDouble(remote.getSodUtc8());
                // 日内涨跌幅也使用起始价格作为基数
                double dayPercent = ((remoteLast - dayStartPrice) / dayStartPrice) * 100;

                return MarketTickersDTO.builder()
                        .instId(remote.getInstId())
                        .threeChangePercent(String.format("%.2f%%", threePercent))
                        .dayChangePercent(String.format("%.2f%%", dayPercent))
                        .build();
            }
        } catch (Exception e) {
            log.warn("计算涨跌幅失败: instId={}, error={}", origin.getInstId(), e.getMessage());
        }
        return null;
    }

    /**
     * 删除多余的市场行情数据（远程数据中不存在的本地数据）
     *
     * @param originMap 本地数据映射
     * @param remoteMap 远程数据映射
     * @param params    xxl-job参数
     */
    private void deleteData(Map<String, MarketTickers> originMap, Map<String, MarketTickers> remoteMap, String[] params) {
        // 获取本地数据中远程不存在的币种（多余）
        List<String> deleteData = CollectionUtil.subtractToList(originMap.keySet(), remoteMap.keySet());
        if (!deleteData.isEmpty()) {
            // 批量删除多余的数据
            tickersService.deleteBatchIds(deleteData, params[0]);
        }
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
