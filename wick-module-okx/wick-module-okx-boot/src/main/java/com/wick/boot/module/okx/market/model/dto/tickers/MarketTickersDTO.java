package com.wick.boot.module.okx.market.model.dto.tickers;

import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 市场行情
 *
 * @author Wickson
 * @date 2024-11-27
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
@ApiModel(description = "市场行情")
public class MarketTickersDTO {

    @ApiModelProperty(value = "产品编号", example = "BTC-USDT-SWAP")
    private String instId;

    @ApiModelProperty(value = "最新价", example = "29100.1")
    private String last;

    @ApiModelProperty(value = "24小时最高价", example = "99490")
    private String high24h;

    @ApiModelProperty(value = "24小时最低价", example = "95620")
    private String low24h;

    @ApiModelProperty(value = "涨跌幅", example = "2.24%")
    private double threeChangePercent;

    @ApiModelProperty(value = "今日涨跌幅", example = "5%")
    private double dayChangePercent;

    @ApiModelProperty(value = "生成时间", example = "2024-11-30 20:45:57")
    private String ts;

    public String getTs() {
        return DateUtil.date(Long.parseLong(ts)).toString();
    }

}
