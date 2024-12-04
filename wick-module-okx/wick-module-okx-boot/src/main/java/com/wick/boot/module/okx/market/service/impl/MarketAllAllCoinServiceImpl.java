package com.wick.boot.module.okx.market.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.api.market.ApiMarketCoin;
import com.wick.boot.module.okx.market.model.dto.allcoin.MarketAllCoinDTO;
import com.wick.boot.module.okx.market.model.vo.allcoin.MarketAllCoinQueryVO;
import com.wick.boot.module.okx.market.service.AbstractMarketAllCoinService;
import com.wick.boot.module.okx.market.service.MarketAllCoinService;
import com.wick.boot.module.okx.model.vo.market.MarketTickersQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 市场行情 - 服务实现层
 *
 * @author Wickson
 * @date 2024-11-19
 */
@Service
@RequiredArgsConstructor
public class MarketAllAllCoinServiceImpl extends AbstractMarketAllCoinService implements MarketAllCoinService {

    private final ApiMarketCoin apiMarketCoin;

    @Override
    public PageResult<MarketAllCoinDTO> getAllCoinPage(MarketAllCoinQueryVO queryVO) {
        // 获取远程市场数据
        List<MarketAllCoinDTO> data = getRemoteData(queryVO.getInstType());

        // 根据排序字段和顺序对数据进行排序
        List<MarketAllCoinDTO> processedData = processData(data, queryVO);

        // 实现分页逻辑
        List<MarketAllCoinDTO> pagedData = paginateData(processedData, queryVO);

        // 返回分页结果
        return new PageResult<>(pagedData, (long) processedData.size());
    }

    private List<MarketAllCoinDTO> getRemoteData(String instType) {
        // 调用 API 获取市场币种数据
        MarketTickersQueryVO marketTickersQueryVO = new MarketTickersQueryVO();
        marketTickersQueryVO.setInstType(instType);
        ForestResponse<String> response = apiMarketCoin.getTickers(marketTickersQueryVO);
        validateResponse(response);

        // 解析响应内容并转换为对象列表
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
        return jsonObject.getJSONArray("data").toList(MarketAllCoinDTO.class);
    }

    /**
     * 根据排序字段和顺序对数据进行排序
     *
     * @param data    数据列表
     * @param queryVO 查询条件对象
     */
    private List<MarketAllCoinDTO> processData(List<MarketAllCoinDTO> data, MarketAllCoinQueryVO queryVO) {
        String billingMethod = queryVO.getBillingMethod();
        String ccy = queryVO.getCcy();
        String sortField = queryVO.getSortField();
        String sortOrder = queryVO.getSortOrder();

        // 数据过滤
        List<MarketAllCoinDTO> filterData = data.stream()
                .filter(item -> StrUtil.isBlank(ccy) || item.getInstId().contains(ccy.toUpperCase()))
                .filter(item -> StrUtil.isBlank(billingMethod) || item.getInstId().contains(billingMethod))
                .collect(Collectors.toList());

        // 动态构造 Comparator
        Comparator<MarketAllCoinDTO> comparator = getComparator(sortField);
        if (comparator != null) {
            if ("descending".equals(sortOrder)) {
                filterData.sort(comparator.reversed());
            } else {
                filterData.sort(comparator);
            }
        }
        return filterData;
    }

    /**
     * 根据字段名获取排序比较器
     *
     * @param sortField 排序字段
     * @return 排序比较器
     */
    private Comparator<MarketAllCoinDTO> getComparator(String sortField) {
        switch (sortField) {
            case "last":
                return Comparator.comparing(dto -> new BigDecimal(dto.getLast()));
            case "changePercent":
                return Comparator.comparing(dto -> new BigDecimal(dto.getChangePercent()));
            default:
                return null;
        }
    }

    /**
     * 根据分页条件对数据进行分页
     *
     * @param data    数据列表
     * @param queryVO 查询条件对象
     * @return 分页后的数据列表
     */
    private List<MarketAllCoinDTO> paginateData(List<MarketAllCoinDTO> data, MarketAllCoinQueryVO queryVO) {
        int pageNum = queryVO.getPageNumber();
        int pageSize = queryVO.getPageSize();
        return CollUtil.emptyIfNull(data).stream()
                .skip((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

}
