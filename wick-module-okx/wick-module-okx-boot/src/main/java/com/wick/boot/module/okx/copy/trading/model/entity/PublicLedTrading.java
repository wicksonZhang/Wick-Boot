package com.wick.boot.module.okx.copy.trading.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 交易员排名-实体类
 *
 * @author Wickson
 * @date 2024-12-04 09:26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("okx_copy_trading_public_led")
public class PublicLedTrading {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 带单规模，单位为USDT
     */
    private String aum;

    /**
     * 当前跟单状态
     */
    private String copyState;

    /**
     * 最大跟单人数
     */
    private String maxCopyTraderNum;

    /**
     * 跟单人数
     */
    private String copyTraderNum;

    /**
     * 累计跟单人数
     */
    private String accCopyTraderNum;

    /**
     * 头像
     */
    private String portLink;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 保证金币种
     */
    private String ccy;

    /**
     * 交易员唯一标识码
     */
    private String uniqueCode;

    /**
     * 胜率
     */
    private String winRatio;

    /**
     * 带单天数
     */
    private String leadDays;

    /**
     * 交易员带单的合约列表
     */
    private String traderInsts;

    /**
     * 近90日交易员收益
     */
    private String pnl;

    /**
     * 近90日交易员收益率
     */
    private String pnlRatio;

    /**
     * 收益率数据
     */
    private String pnlRatios;

}