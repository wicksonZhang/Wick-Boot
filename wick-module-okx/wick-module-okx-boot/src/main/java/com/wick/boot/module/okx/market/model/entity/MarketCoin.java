package com.wick.boot.module.okx.market.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2024-11-21 21:57
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
     * 最新成交的数量，0代表没有成交量
     */
    @TableField(value = "last_sz")
    private String lastSz;

    /**
     * 卖一价
     */
    @TableField(value = "ask_px")
    private String askPx;

    /**
     * 卖一价的挂单数量
     */
    @TableField(value = "ask_sz")
    private String askSz;

    /**
     * 买一价
     */
    @TableField(value = "bid_px")
    private String bidPx;

    /**
     * 买一价的挂单数量
     */
    @TableField(value = "bid_sz")
    private String bidSz;

    /**
     * 24小时开盘价
     */
    @TableField(value = "open_24h")
    private String open24h;

    /**
     * 24小时最高价
     */
    @TableField(value = "high_24h")
    private String high24h;

    /**
     * 24小时最低价
     */
    @TableField(value = "low_24h")
    private String low24h;

    /**
     * 24小时成交量，以币为单位
     */
    @TableField(value = "vol_ccy_24h")
    private String volCcy24h;

    /**
     * 24小时成交量，以张为单位
     */
    @TableField(value = "vol_24h")
    private String vol24h;

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

    /**
     * 是否自选(0-否、1-是)
     */
    @TableField(value = "is_favorite")
    private Integer favorite;

}