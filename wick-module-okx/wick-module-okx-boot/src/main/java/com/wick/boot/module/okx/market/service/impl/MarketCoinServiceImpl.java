package com.wick.boot.module.okx.market.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.api.market.ApiMarketCoin;
import com.wick.boot.module.okx.market.model.dto.allcoin.MarketAllCoinDTO;
import com.wick.boot.module.okx.market.model.vo.allcoin.MarketAllCoinQueryVO;
import com.wick.boot.module.okx.market.service.AbstractMarketCoinService;
import com.wick.boot.module.okx.market.service.MarketCoinService;
import com.wick.boot.module.okx.model.market.MarketTickersQueryVO;
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
public class MarketCoinServiceImpl extends AbstractMarketCoinService implements MarketCoinService {

    private final ApiMarketCoin apiMarketCoin;

    @Override
    public PageResult<MarketAllCoinDTO> getAllCoinPage(MarketAllCoinQueryVO queryVO) {
        // 调用 API 获取数据
        MarketTickersQueryVO marketTickersQueryVO = BeanUtil.copyProperties(queryVO, MarketTickersQueryVO.class);
        ForestResponse<String> response = apiMarketCoin.getTickers(marketTickersQueryVO);
        this.validateResponse(response);

        // 类型转换
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
        List<MarketAllCoinDTO> data = jsonObject.getJSONArray("data").toList(MarketAllCoinDTO.class);

        // 新增数据排序
        data = data.stream()
                .filter(item -> item.getInstId() != null && item.getInstId().contains("USDT"))
                .collect(Collectors.toList());

        // 根据字段排序
        sortData(data, queryVO);

        // 手动分页逻辑
        int pageNum = queryVO.getPageNumber();
        int pageSize = queryVO.getPageSize();
        long total = data.size();

        List<MarketAllCoinDTO> pagedData = CollUtil.emptyIfNull(data).stream()
                .skip((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        // 构造分页结果
        return new PageResult<>(pagedData, total);
    }

    /**
     * 动态排序方法
     *
     * @return
     */
    private void sortData(List<MarketAllCoinDTO> data, MarketAllCoinQueryVO queryVO) {
        // 获取排序字段和顺序
        String sortField = queryVO.getSortField();
        String sortOrder = queryVO.getSortOrder();

        if (StrUtil.isBlank(sortField)) {
            return;
        }

        // 根据不同的排序字段创建对应的 Comparator
        Comparator<MarketAllCoinDTO> comparator = null;
        switch (sortField) {
            case "last":
                comparator = Comparator.comparing(dto -> {
                    try {
                        return new BigDecimal(dto.getLast());
                    } catch (Exception e) {
                        return BigDecimal.ZERO;
                    }
                });
                break;
            case "changePercent":
                comparator = Comparator.comparing(dto -> {
                    try {
                        return new BigDecimal(dto.getChangePercent());
                    } catch (Exception e) {
                        return BigDecimal.ZERO;
                    }
                });
                break;
        }

        // 如果找到有效的比较器，则进行排序
        if (comparator != null) {
            if ("descending".equals(sortOrder)) {
                data.sort(comparator.reversed());
            } else if ("ascending".equals(sortOrder)) {
                data.sort(comparator);
            }
        }
    }

    private void validateResponse(ForestResponse<String> response) {
        int statusCode = response.statusCode();
        if (HttpStatus.HTTP_OK != statusCode) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

}
