package com.wick.boot.module.okx.market.mapper;

import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.entity.MarketCoin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 币种信息管理-Mapper接口
 *
 * @author Wickson
 * @date 2024-11-21 23:35
 */
@Mapper
public interface MarketCoinMapper extends BaseMapperX<MarketCoin> {

    /**
     * 查询所有币种信息
     *
     * @return
     */
    List<MarketCoin> selectList();

}
