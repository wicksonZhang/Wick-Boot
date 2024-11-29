package com.wick.boot.module.okx.market.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.entity.MarketMyCoin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 我的自选管理-Mapper接口
 *
 * @date 2024-11-29 17:42
 */
@Mapper
public interface MarketMyCoinMapper extends BaseMapperX<MarketMyCoin> {

    default Long selectCountByInstIdAndInstType(String instId, String instType) {
        LambdaQueryWrapper<MarketMyCoin> wrapper = new LambdaQueryWrapper<>();
        return this.selectCount(wrapper.eq(MarketMyCoin::getInstId, instId).eq(MarketMyCoin::getInstType, instType));
    }
}