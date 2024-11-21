package com.wick.boot.module.okx.market.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.entity.MarketCoin;
import com.wick.boot.module.okx.market.model.vo.MarketAllCoinQueryVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 币种信息管理-Mapper接口
 *
 * @date 2024-11-21 23:35
 */
@Mapper
public interface MarketCoinMapper extends BaseMapperX<MarketCoin> {

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryVO 请求参数
     * @return 数据表分页集合
     */
    default Page<MarketCoin> getMarketCoinPage(Page<MarketCoin> page, MarketAllCoinQueryVO queryVO) {
        LambdaQueryWrapper<MarketCoin> wrapper = new LambdaQueryWrapper<>();
        wrapper
            .select(
                MarketCoin::getId,
                MarketCoin::getInstType,
                MarketCoin::getInstId,
                MarketCoin::getCreateTime,
                MarketCoin::getUpdateTime
            )
            .orderByDesc(MarketCoin::getId);

        return selectPage(page, wrapper);
    }
}
