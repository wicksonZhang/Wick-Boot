package com.wick.boot.module.okx.market.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.market.model.vo.allcoin.MarketAllCoinQueryVO;
import com.wick.boot.module.okx.market.model.dto.allcoin.MarketAllCoinDTO;

/**
 * 市场行情 - 服务层
 *
 * @author Wickson
 * @date 2024-11-19
 */
public interface MarketCoinService {

    /**
     * 分页查询_市场行情
     *
     * @param queryVO 查询参数
     * @return
     */
    PageResult<MarketAllCoinDTO> getAllCoinPage(MarketAllCoinQueryVO queryVO);

}
