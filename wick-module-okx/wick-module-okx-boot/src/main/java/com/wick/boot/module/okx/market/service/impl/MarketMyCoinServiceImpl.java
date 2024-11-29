package com.wick.boot.module.okx.market.service.impl;

import com.wick.boot.module.okx.market.convert.MarketMyCoinConvert;
import com.wick.boot.module.okx.market.mapper.MarketMyCoinMapper;
import com.wick.boot.module.okx.market.model.entity.MarketMyCoin;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;
import com.wick.boot.module.okx.market.service.MarketMyCoinAbstractService;
import com.wick.boot.module.okx.market.service.MarketMyCoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 我的自选管理-服务实现类
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
@Slf4j
@Service
public class MarketMyCoinServiceImpl extends MarketMyCoinAbstractService implements MarketMyCoinService {

    @Resource
    private MarketMyCoinMapper marketMyCoinMapper;

    /**
     * 新增我的自选数据
     *
     * @param reqVO 新增请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addMarketMyCoin(MarketMyCoinAddVO reqVO) {
        /* Step-1: 校验新增参数 */
        this.validateAddParams(reqVO);

        /* Step-2: 转换实体类型 */
        MarketMyCoin marketMyCoin = MarketMyCoinConvert.INSTANCE.addVoToEntity(reqVO);

        /* Step-3: 保存我的自选信息 */
        this.marketMyCoinMapper.insert(marketMyCoin);
        return marketMyCoin.getId();
    }

}
