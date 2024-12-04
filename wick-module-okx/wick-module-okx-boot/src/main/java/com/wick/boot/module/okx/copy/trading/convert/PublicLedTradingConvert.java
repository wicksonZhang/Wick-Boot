package com.wick.boot.module.okx.copy.trading.convert;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.wick.boot.module.okx.copy.trading.model.dto.PublicLedTradingDTO;
import com.wick.boot.module.okx.model.dto.copy.trading.PublicLeadTradersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 交易员排名管理-转换类
 *
 * @author Wickson
 * @date 2024-12-04 09:26
 */
@Mapper
public interface PublicLedTradingConvert {

    PublicLedTradingConvert INSTANCE = Mappers.getMapper(PublicLedTradingConvert.class);

    /**
     * 实体 转换 分页集合
     *
     * @param ranks 交易员排名信息实体集合
     * @return
     */
    List<PublicLedTradingDTO> entityToPage(List<PublicLeadTradersDTO.Rank> ranks);

    /**
     * DTO 转换 实体
     *
     * @param rank
     * @return
     */
    @Mappings({
            @Mapping(target = "traderInsts", expression = "java(convertTraderInsts(rank))"),
            @Mapping(target = "pnlRatios", expression = "java(convertPnlRatios(rank))")
    })
    PublicLedTradingDTO convertToEntity(PublicLeadTradersDTO.Rank rank);

    /**
     * 转换交易员带单的合约列表
     *
     * @param rank 交易员排名信息
     * @return 交易员带单的合约列表
     */
    default String convertTraderInsts(PublicLeadTradersDTO.Rank rank) {
        List<String> traderInsts = rank.getTraderInsts();
        if (CollUtil.isEmpty(traderInsts)) {
            return null;
        }
        return JSONUtil.toJsonStr(traderInsts);
    }

    /**
     * 转换交易员带单的合约列表
     *
     * @param rank 交易员排名信息
     * @return 交易员带单的合约列表
     */
    default String convertPnlRatios(PublicLeadTradersDTO.Rank rank) {
        List<String> pnlRatios = rank.getPnlRatios();
        if (CollUtil.isEmpty(pnlRatios)) {
            return null;
        }
        return JSONUtil.toJsonStr(pnlRatios);
    }

}