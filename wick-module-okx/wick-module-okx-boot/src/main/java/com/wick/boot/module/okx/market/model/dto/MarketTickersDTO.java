package com.wick.boot.module.okx.market.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 市场行情
 *
 * @author Wickson
 * @date 2024-11-19
 */
@Setter
@Getter
@ApiModel(description = "市场行情")
public class MarketTickersDTO {

    @ApiModelProperty(value = "产品类型", position = 1)
    private String instType;

    @ApiModelProperty(value = "产品ID", position = 2)
    private String instId;

    @ApiModelProperty(value = "最新成交价", position = 3)
    private String last;

    @ApiModelProperty(value = "最新成交的数量，0代表没有成交量", position = 4)
    private String lastSz;

    @ApiModelProperty(value = "卖一价", position = 5)
    private String askPx;

    @ApiModelProperty(value = "卖一价的挂单数量", position = 6)
    private String askSz;

    @ApiModelProperty(value = "买一价", position = 7)
    private String bidPx;

    @ApiModelProperty(value = "买一价的挂单数量", position = 8)
    private String bidSz;

    @ApiModelProperty(value = "24小时开盘价", position = 9)
    private String open24h;

    @ApiModelProperty(value = "24小时最高价", position = 10)
    private String high24h;

    @ApiModelProperty(value = "24小时最低价", position = 11)
    private String low24h;

    @ApiModelProperty(value = "24小时成交量，以币为单位", position = 12)
    private String volCcy24h;

    @ApiModelProperty(value = "24小时成交量，以张为单位", position = 13)
    private String vol24h;

    @ApiModelProperty(value = "UTC 0 时开盘价", position = 14)
    private String sodUtc0;

    @ApiModelProperty(value = "UTC+8 时开盘价", position = 15)
    private String sodUtc8;

    @ApiModelProperty(value = "ticker数据产生时间，Unix时间戳的毫秒数格式", position = 16)
    private String ts;

    @ApiModelProperty(value = "涨跌幅", position = 17)
    private String changePercent;

    public String getTs() {
        try {
            if (ts == null || ts.isEmpty() || Long.parseLong(ts) == 0) {
                return null;
            }
            long triggerLastTime = Long.parseLong(this.ts);
            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(
                    triggerLastTime / 1000,
                    0,
                    ZoneOffset.ofHours(8)
            );
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(dateTime);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getChangePercent() {
        double lastPrice = Double.parseDouble(last);
        double startPrice = Double.parseDouble(sodUtc8);

        if (lastPrice == 0 || startPrice == 0) {
            return "--";
        }
        double percent = ((lastPrice - startPrice) / startPrice) * 100;
        return String.format("%.2f", percent);
    }

}
