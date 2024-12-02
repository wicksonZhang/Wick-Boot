package com.wick.boot.module.okx.market.convert;

import cn.hutool.core.date.DateUtil;
import com.wick.boot.module.okx.market.model.dto.mycoin.MarketMyCoinDTO;
import com.wick.boot.module.okx.market.model.entity.MarketMyCoin;
import com.wick.boot.module.okx.market.model.vo.mycoin.MarketMyCoinAddVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 我的自选管理-转换类
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
@Mapper
public interface MarketMyCoinConvert {

    MarketMyCoinConvert INSTANCE = Mappers.getMapper(MarketMyCoinConvert.class);

    /**
     * Convert addVo To entity
     *
     * @param reqVO 新增请求参数VO
     * @return MarketMyCoin 我的自选
     */
    MarketMyCoin addVoToEntity(MarketMyCoinAddVO reqVO);

    /**
     * Convert entity To page
     *
     * @param records 我的自选列表
     * @return
     */
    List<MarketMyCoinDTO> entityToPage(List<MarketMyCoin> records);

    default String convertTs() {
        return DateUtil.now();
    }
}