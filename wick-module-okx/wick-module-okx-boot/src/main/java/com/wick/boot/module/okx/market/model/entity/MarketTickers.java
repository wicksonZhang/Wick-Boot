package com.wick.boot.module.okx.market.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 市场行情-实体类
 *
 * @author Wickson
 * @date 2024-11-21 21:57
 */
@Getter
@Setter
@Accessors(chain = true)
public class MarketTickers {

    /**
     * 产品类型(SWAP-永续合约、SPOT-币币、FUTURES-交割合约、OPTION-期权)
     */
    private String instType;

    /**
     * 产品ID
     */
    private String instId;

    /**
     * 最新成交价
     */
    private String last;

    /**
     * 24小时最高价
     */
    private String high24h;

    /**
     * 24小时最低价
     */
    private String low24h;

    /**
     * 最新成交的数量，0代表没有成交量
     */
    @TableField(value = "last_sz")
    private String lastSz;

    /**
     * UTC 0 时开盘价
     */
    @TableField(value = "sod_utc0")
    private String sodUtc0;

    /**
     * UTC+8 时开盘价
     */
    @TableField(value = "sod_utc8")
    private String sodUtc8;

    /**
     * ticker数据产生时间，Unix时间戳的毫秒数格式
     */
    private String ts;

}