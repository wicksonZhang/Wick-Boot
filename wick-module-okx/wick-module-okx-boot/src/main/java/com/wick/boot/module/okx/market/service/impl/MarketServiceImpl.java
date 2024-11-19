package com.wick.boot.module.okx.market.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.api.market.ApiMarket;
import com.wick.boot.module.okx.market.model.dto.MarketTickersDTO;
import com.wick.boot.module.okx.market.model.vo.MarketTickersQueryVO;
import com.wick.boot.module.okx.market.service.MarketService;
import com.wick.boot.module.okx.model.vo.market.TickersQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class MarketServiceImpl implements MarketService {

    private final ApiMarket apiMarket;

    @Override
    public PageResult<MarketTickersDTO> getTickersPage(MarketTickersQueryVO queryVO) {
        // 调用 API 获取数据
        TickersQueryVO tickersQueryVO = BeanUtil.copyProperties(queryVO, TickersQueryVO.class);
        ForestResponse<String> response = apiMarket.getTickers(tickersQueryVO);
        this.validateResponse(response);

        // 类型转换
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
        List<MarketTickersDTO> data = jsonObject.getJSONArray("data").toList(MarketTickersDTO.class).stream()
                .sorted((a, b) -> {
                    try {
                        BigDecimal lastA = new BigDecimal(a.getLast());
                        BigDecimal lastB = new BigDecimal(b.getLast());
                        return lastB.compareTo(lastA);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .collect(Collectors.toList());

        // 手动分页逻辑
        int pageNum = queryVO.getPageNumber();
        int pageSize = queryVO.getPageSize();
        long total = data.size();

        List<MarketTickersDTO> pagedData = CollUtil.emptyIfNull(data).stream()
                .skip((long) (pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        // 构造分页结果
        return new PageResult<>(pagedData, total);
    }

    private void validateResponse(ForestResponse<String> response) {
        int statusCode = response.statusCode();
        if (HttpStatus.HTTP_OK != statusCode) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

}
