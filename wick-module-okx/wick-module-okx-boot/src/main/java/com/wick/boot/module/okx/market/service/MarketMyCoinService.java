package com.wick.boot.module.okx.market.service;

import com.wick.boot.common.core.result.PageResult;
import com.wick.boot.module.okx.market.model.dto.mycoin.MarketMyCoinDTO;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinQueryVO;

import java.util.List;

/**
 * 我的自选-应用服务类
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
public interface MarketMyCoinService {

    /**
     * 新增我的自选数据
     *
     * @param reqVO 新增请求参数
     */
    Long addMarketMyCoin(MarketMyCoinAddVO reqVO);

    /**
     * 取消自选
     *
     * @param ids 自选id列表
     */
    void deleteMarketMyCoin(List<Long> ids);

    /**
     * 获取我的自选分页数据
     *
     * @param reqVO 查询请求参数
     * @return
     */
    PageResult<MarketMyCoinDTO> getMarketMyCoinPage(MarketMyCoinQueryVO reqVO);
}