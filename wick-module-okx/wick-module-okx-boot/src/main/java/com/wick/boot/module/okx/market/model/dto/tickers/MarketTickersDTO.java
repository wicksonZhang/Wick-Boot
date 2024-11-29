package com.wick.boot.module.okx.market.model.dto.tickers;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ApiModel(description = "市场行情")
public class MarketTickersDTO {

    @ApiModelProperty(value = "产品编号", example = "BTC-USDT-SWAP")
    private String instId;

    @ApiModelProperty(value = "涨跌幅", example = "2.24%")
    private String threeChangePercent;

    @ApiModelProperty(value = "今日涨跌幅", example = "5%")
    private String dayChangePercent;

}
