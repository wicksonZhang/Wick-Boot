package com.wick.boot.module.okx.market.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wick.boot.common.core.model.entity.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 市场行情-实体类
 *
 * @author Wickson
 * @date 2024-11-28 15:34
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("okx_market_coin_close")
public class MarketCoinClose extends BaseDO {

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

    /**
     * UTC+8 时开盘价(格林尼治开盘价)
     */
    private String sodUtc8;

    /**
     * UTC 0 时开盘价(北京时间开盘价)
     */
    private String sodUtc0;

    /**
     * 生成时间
     */
    private String ts;
}