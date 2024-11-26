package com.wick.boot.module.okx.market.service.impl;

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
        // 调用 API 获取市场币种数据
        MarketTickersQueryVO marketTickersQueryVO = new MarketTickersQueryVO();
        marketTickersQueryVO.setInstType(queryVO.getInstType());
        ForestResponse<String> response = apiMarketCoin.getTickers(marketTickersQueryVO);
        validateResponse(response);

        // 解析响应内容并转换为对象列表
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
        List<MarketAllCoinDTO> data = jsonObject.getJSONArray("data").toList(MarketAllCoinDTO.class);

        // 根据条件过滤数据
        data = filterData(data, queryVO);

        // 根据用户指定的字段和排序顺序进行排序
        sortData(data, queryVO);

        // 实现分页逻辑
        List<MarketAllCoinDTO> pagedData = paginateData(data, queryVO);

        // 返回分页结果
        return new PageResult<>(pagedData, (long) data.size());
    }

    /**
     * 根据查询条件过滤数据
     * @param data 原始数据列表
     * @param queryVO 查询条件对象
     * @return 过滤后的数据列表
     */
    private List<MarketAllCoinDTO> filterData(List<MarketAllCoinDTO> data, MarketAllCoinQueryVO queryVO) {
        String billingMethod = queryVO.getBillingMethod();
        String ccy = queryVO.getCcy();
        return data.stream()
                .filter(item -> StrUtil.isBlank(ccy) || item.getInstId().contains(ccy.toUpperCase()))
                .filter(item -> StrUtil.isBlank(billingMethod) || item.getInstId().contains(billingMethod))
                .collect(Collectors.toList());
    }

    /**
     * 根据排序字段和顺序对数据进行排序
     * @param data 数据列表
     * @param queryVO 查询条件对象
     */
    private void sortData(List<MarketAllCoinDTO> data, MarketAllCoinQueryVO queryVO) {
        String sortField = queryVO.getSortField();
        String sortOrder = queryVO.getSortOrder();

        if (StrUtil.isBlank(sortField)) {
            return;
        }

        // 创建对应的排序比较器
        Comparator<MarketAllCoinDTO> comparator = getComparator(sortField);

        // 如果比较器有效，根据顺序排序
        if (comparator != null) {
            if ("descending".equalsIgnoreCase(sortOrder)) {
                data.sort(comparator.reversed());
            } else {
                data.sort(comparator);
            }
        }
    }

    /**
     * 根据字段名获取排序比较器
     * @param sortField 排序字段
     * @return 排序比较器
     */
    private Comparator<MarketAllCoinDTO> getComparator(String sortField) {
        switch (sortField) {
            case "last":
                return Comparator.comparing(dto -> parseBigDecimal(dto.getLast()));
            case "changePercent":
                return Comparator.comparing(dto -> parseBigDecimal(dto.getChangePercent()));
            default:
                return null;
        }
    }

    /**
     * 安全解析字符串为 BigDecimal
     * @param value 字符串值
     * @return 解析后的 BigDecimal，如果解析失败则返回零
     */
    private BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 根据分页条件对数据进行分页
     * @param data 数据列表
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

    /**
     * 验证 API 响应的状态码是否正常
     * @param response Forest 响应对象
     */
    private void validateResponse(ForestResponse<String> response) {
        if (HttpStatus.HTTP_OK != response.statusCode()) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }
}
