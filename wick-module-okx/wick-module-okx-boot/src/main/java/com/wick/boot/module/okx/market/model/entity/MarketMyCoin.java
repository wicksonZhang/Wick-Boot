package com.wick.boot.module.okx.market.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 我的自选-实体类
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("okx_market_my_coin")
public class MarketMyCoin extends BaseDO {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品ID
     */
    private String instId;

    /**
     * 产品类型
     */
    private String instType;

    /**
     * 币种类型（MeMe、Layer1、NFT）
     */
    private String coinType;

}