package com.wick.boot.module.okx.copy.trading.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.boot.common.mybatis.mapper.BaseMapperX;
import com.wick.boot.module.okx.copy.trading.model.entity.PublicLedTrading;
import com.wick.boot.module.okx.copy.trading.model.vo.PublicLedTradingQueryVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 交易员排名管理-Mapper接口
 *
 * @author Wickson
 * @date 2024-12-04 09:26
 */
@Mapper
public interface PublicLedTradingMapper extends BaseMapperX<PublicLedTrading> {

    /**
     * 删除所有数据
     */
    void deleteAll();

    /**
     * 分页查询数据表
     *
     * @param page    分页集合
     * @param queryParams 请求参数
     * @return 数据表分页集合
     */
    Page<PublicLedTrading> getPublicLedTradingPage(Page<PublicLedTrading> page, PublicLedTradingQueryVO queryParams);
}
