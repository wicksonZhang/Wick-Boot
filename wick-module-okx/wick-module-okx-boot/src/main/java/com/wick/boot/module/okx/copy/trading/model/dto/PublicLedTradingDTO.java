package com.wick.boot.module.okx.copy.trading.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 交易员排名-DTO
 *
 * @author Wickson
 * @date 2024-12-04 10:30
 */
@Getter
@Setter
@ApiModel(value = "PublicLedTradingDTO对象", description = "交易员排名视图DTO")
public class PublicLedTradingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易员主键")
    private Long id;

    @ApiModelProperty(value = "带单规模，单位为USDT")
    private String aum;

    @ApiModelProperty(value = "当前跟单状态")
    private String copyState;

    @ApiModelProperty(value = "最大跟单人数")
    private String maxCopyTraderNum;

    @ApiModelProperty(value = "跟单人数")
    private String copyTraderNum;

    @ApiModelProperty(value = "累计跟单人数")
    private String accCopyTraderNum;

    @ApiModelProperty(value = "头像")
    private String portLink;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "保证金币种")
    private String ccy;

    @ApiModelProperty(value = "交易员唯一标识码")
    private String uniqueCode;

    @ApiModelProperty(value = "胜率")
    private String winRatio;

    @ApiModelProperty(value = "带单天数")
    private String leadDays;

    @ApiModelProperty(value = "交易员带单的合约列表")
    private String traderInsts;

    @ApiModelProperty(value = "近90日交易员收益")
    private String pnl;

    @ApiModelProperty(value = "近90日交易员收益率")
    private String pnlRatio;

    @ApiModelProperty(value = "收益率数据")
    private String pnlRatios;

}