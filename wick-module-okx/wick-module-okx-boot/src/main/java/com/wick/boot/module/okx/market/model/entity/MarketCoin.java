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
     * 产品类型
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
    private String lastSz;

    /**
     * 卖一价
     */
    private String askPx;

    /**
     * 卖一价的挂单数量
     */
    private String askSz;

    /**
     * 买一价
     */
    private String bidPx;

    /**
     * 买一价的挂单数量
     */
    private String bidSz;

    /**
     * 24小时开盘价
     */
    private String open24h;

    /**
     * 24小时最高价
     */
    private String high24h;

    /**
     * 24小时最低价
     */
    private String low24h;

    /**
     * 24小时成交量，以币为单位
     */
    private String volCcy24h;

    /**
     * 24小时成交量，以张为单位
     */
    private String vol24h;

    /**
     * UTC 0 时开盘价
     */
    private String sodUtc0;

    /**
     * UTC+8 时开盘价
     */
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