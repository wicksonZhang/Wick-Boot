package com.wick.boot.module.okx.copy.trading.service.impl;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dtflys.forest.http.ForestResponse;
import com.wick.boot.common.core.constant.GlobalResultCodeConstants;
import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.api.copy.trading.ApiCopyTrading;
import com.wick.boot.module.okx.copy.trading.convert.PublicLedTradingConvert;
import com.wick.boot.module.okx.copy.trading.model.dto.PublicLedTradingDTO;
import com.wick.boot.module.okx.copy.trading.model.vo.PublicLedTradingQueryVO;
import com.wick.boot.module.okx.copy.trading.service.PublicLedTradingAbstractService;
import com.wick.boot.module.okx.copy.trading.service.PublicLedTradingService;
import com.wick.boot.module.okx.model.dto.copy.trading.PublicLeadTradersDTO;
import com.wick.boot.module.okx.model.vo.copy.trading.PublicLeadTradersQueryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 交易员排名管理-服务实现类
 *
 * @author Wickson
 * @date 2024-12-04 10:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PublicLedTradingServiceImpl extends PublicLedTradingAbstractService implements PublicLedTradingService {

    private final ApiCopyTrading copyTrading;

    @Override
    public PageResult<PublicLedTradingDTO> getPublicLedTradingPage(PublicLedTradingQueryVO queryParams) {
        // Step-1： 获取公共跟单交易员列表
        PublicLeadTradersDTO tradersDto = fetchRemoteData(queryParams);
        if (ObjUtil.isNull(tradersDto)) {
            log.info("获取交易员排行数据失败");
            return PageResult.empty();
        }

        // Step-2： 获取分页数据
        List<PublicLeadTradersDTO.Rank> ranks = tradersDto.getRanks();
        List<PublicLedTradingDTO> publicLedTradingPages = PublicLedTradingConvert.INSTANCE.entityToPage(ranks);

        // Step-3： 返回分页结果
        long total = Long.parseLong(tradersDto.getTotalPage()) * queryParams.getPageSize();
        return new PageResult<>(publicLedTradingPages, total);
    }

    private PublicLeadTradersDTO fetchRemoteData(PublicLedTradingQueryVO queryParams) {
        // 调用 API 获取市场币种数据
        PublicLeadTradersQueryVO queryVO = new PublicLeadTradersQueryVO();
        queryVO.setPage(queryParams.getPageNumber().toString());
        queryVO.setLimit(queryParams.getPageSize().toString());
        queryVO.setSortType(NamingCase.toUnderlineCase(queryParams.getSortField()));
        ForestResponse<String> response = copyTrading.getPublicLeadTraders(queryVO);

        // 验证响应结果集
        validateResponse(response);

        // 解析响应内容并转换为对象列表
        JSONObject jsonObject = JSONUtil.parseObj(response.getContent());
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<PublicLeadTradersDTO> tradersDtoList = jsonArray.toList(PublicLeadTradersDTO.class);
        return tradersDtoList.isEmpty() ? null : tradersDtoList.get(0);
    }

    private void validateResponse(ForestResponse<String> response) {
        if (HttpStatus.HTTP_OK != response.statusCode()) {
            throw ServiceException.getInstance(GlobalResultCodeConstants.FAIL);
        }
    }

}