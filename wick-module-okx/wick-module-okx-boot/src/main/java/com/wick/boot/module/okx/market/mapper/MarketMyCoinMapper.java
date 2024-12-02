package com.wick.boot.module.okx.market.mapper;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.entity.MarketMyCoin;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinQueryVO;
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

    default Page<MarketMyCoin> getMarketMyCoinPage(Page<MarketMyCoin> page, MarketMyCoinQueryVO queryParams) {
        LambdaQueryWrapper<MarketMyCoin> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(StrUtil.isNotBlank(queryParams.getCoinName()), MarketMyCoin::getInstId, queryParams.getCoinName());
        return this.selectPage(page, wrapper);
    }
}