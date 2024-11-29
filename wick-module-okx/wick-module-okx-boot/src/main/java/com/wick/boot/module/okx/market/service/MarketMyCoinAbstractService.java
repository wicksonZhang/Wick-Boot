package com.wick.boot.module.okx.market.service;

import com.wick.boot.common.core.exception.ServiceException;
import com.wick.boot.module.okx.market.mapper.MarketMyCoinMapper;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;

import javax.annotation.Resource;

import static com.wick.boot.module.okx.enums.market.ErrorCodeMarket.MY_COIN_EXISTING;

/**
 * 我的自选管理-防腐层
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
public abstract class MarketMyCoinAbstractService {

    @Resource
    private MarketMyCoinMapper marketMyCoinMapper;

    /**
     * 校验新增参数
     *
     * @param reqVO 新增参数
     */
    protected void validateAddParams(MarketMyCoinAddVO reqVO) {
        // 校验自选是否存在
        validateMyCoinExisting(reqVO.getInstId(), reqVO.getInstType());
    }

    /**
     * 校验自选是否存在
     *
     * @param instId   产品名称
     * @param instType 产品类型
     */
    private void validateMyCoinExisting(String instId, String instType) {
        // 校验自选是否存在
        if (marketMyCoinMapper.selectCountByInstIdAndInstType(instId, instType) > 0) {
            throw ServiceException.getInstance(MY_COIN_EXISTING);
        }
    }

}