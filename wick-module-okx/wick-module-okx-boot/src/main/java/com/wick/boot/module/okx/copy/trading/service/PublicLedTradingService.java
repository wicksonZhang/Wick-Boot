package com.wick.boot.module.okx.copy.trading.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.copy.trading.model.dto.PublicLedTradingDTO;
import com.wick.boot.module.okx.copy.trading.model.vo.PublicLedTradingQueryVO;

/**
 * 交易员排名-应用服务类
 *
 * @author Wickson
 * @date 2024-12-04 10:30
 */
public interface PublicLedTradingService {

    /**
     * 获取交易员排名分页数据
     *
     * @param queryParams 分页查询参数
     * @return PublicLedTradingDTO 交易员排名DTO
     */
    PageResult<PublicLedTradingDTO> getPublicLedTradingPage(PublicLedTradingQueryVO queryParams);

}