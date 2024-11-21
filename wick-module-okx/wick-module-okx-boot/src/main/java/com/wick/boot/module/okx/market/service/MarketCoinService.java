package com.wick.boot.module.okx.market.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.market.model.dto.MarketCoinAddVO;
import com.wick.boot.module.okx.market.model.vo.MarketAllCoinQueryVO;
import com.wick.boot.module.okx.market.model.dto.MarketCoinDTO;

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
    PageResult<MarketCoinDTO> getAllCoinPage(MarketAllCoinQueryVO queryVO);

    /**
     * 新增我的自选币种
     *
     * @param reqVO 新增参数
     */
    void addMarketMyCoin(MarketCoinAddVO reqVO);
}
