package com.wick.boot.module.okx.market.model.dto.mycoin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 我的自选-DTO
 *
 * @author Wickson
 * @date 2024-12-02 11:04
 */
@Getter
@Setter
@ApiModel(value = "MarketMyCoinDTO对象", description = "我的自选视图DTO")
public class MarketMyCoinDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "产品ID")
    private String instId;

    @ApiModelProperty(value = "产品类型")
    private String instType;

    @ApiModelProperty(value = "币种类型（MeMe、Layer1、NFT）")
    private String coinType;

    @ApiModelProperty(value = "最新成交价", example = "98893.7")
    private String last;

    @ApiModelProperty(value = "24小时最高价", example = "99490")
    private String high24h;

    @ApiModelProperty(value = "24小时最低价", example = "95620")
    private String low24h;

    @ApiModelProperty(value = "UTC 0 时开盘价", example = "98338.6")
    private String sodUtc0;

    @ApiModelProperty(value = "UTC+8 时开盘价", example = "96687.5")
    private String sodUtc8;

    @ApiModelProperty(value = "ticker数据产生时间，Unix时间戳的毫秒数格式", example = "1699376100000")
    private String ts;

    @ApiModelProperty(value = "涨跌幅", example = "2.24%")
    private String changePercent;
}


