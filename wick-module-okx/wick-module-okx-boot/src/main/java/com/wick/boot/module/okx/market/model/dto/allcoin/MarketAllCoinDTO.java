package com.wick.boot.module.okx.market.model.dto.allcoin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 所有币种
 *
 * @author Wickson
 * @date 2024-11-19
 */
@Setter
@Getter
@ToString
@ApiModel(description = "市场行情")
public class MarketAllCoinDTO {

    @ApiModelProperty(value = "产品类型", example = "SWAP")
    private String instType;

    @ApiModelProperty(value = "产品编号", example = "BTC-USDT-SWAP")
    private String instId;

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
            return "0";
        }
        double percent = ((lastPrice - startPrice) / startPrice) * 100;
        return String.format("%.2f", percent);
    }

}
