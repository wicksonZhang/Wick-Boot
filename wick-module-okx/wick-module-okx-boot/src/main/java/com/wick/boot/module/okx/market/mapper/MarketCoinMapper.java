package com.wick.boot.module.okx.market.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.market.model.dto.allcoin.MarketAllCoinDTO;
import com.wick.boot.module.okx.market.model.entity.MarketCoin;
import com.wick.boot.module.okx.market.model.vo.allcoin.MarketAllCoinQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 分页查询所有币种信息
     *
     * @param page        分页信息
     * @param queryParams 查询参数
     * @return
     */
    Page<MarketAllCoinDTO> getMarketAllCoinPage(Page<MarketCoin> page, @Param("reqVO") MarketAllCoinQueryVO queryParams);
}
