package com.wick.boot.module.okx.market.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 币种信息-实体类
 *
 * @author Wickson
 * @date 2024-11-21 23:33
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("okx_market_coin")
public class MarketCoin extends BaseDO {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品类型
     */
    private String instType;

    /**
     * 产品ID
     */
    private String instId;

}