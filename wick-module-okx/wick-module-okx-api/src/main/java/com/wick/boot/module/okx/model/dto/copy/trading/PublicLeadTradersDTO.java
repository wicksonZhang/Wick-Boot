package com.wick.boot.module.okx.model.dto.copy.trading;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 交易员排名 DTO
 *
 * @author Wickson
 * @date 2024-12-02
 */
@Data
@ToString
@ApiModel(description = "交易员排名DTO")
public class PublicLeadTradersDTO {

    @ApiModelProperty(value = "排名数据的版本", example = "20231010182400")
    private String dataVer;

    @ApiModelProperty(value = "总的页数", example = "10")
    private String totalPage;

    @ApiModelProperty(value = "交易员排名信息")
    private List<Rank> ranks;

    @Getter
    @Setter
    @ApiModel(description = "交易员排名信息")
    public static class Rank {
        @ApiModelProperty(value = "带单规模，单位为USDT", example = "10000")
        private String aum;

        @ApiModelProperty(value = "当前跟单状态", example = "active")
        private String copyState;

        @ApiModelProperty(value = "最大跟单人数", example = "100")
        private String maxCopyTraderNum;

        @ApiModelProperty(value = "跟单人数", example = "50")
        private String copyTraderNum;

        @ApiModelProperty(value = "累计跟单人数", example = "200")
        private String accCopyTraderNum;

        @ApiModelProperty(value = "头像", example = "https://example.com/avatar.jpg")
        private String portLink;

        @ApiModelProperty(value = "昵称", example = "TraderX")
        private String nickName;

        @ApiModelProperty(value = "保证金币种", example = "USDT")
        private String ccy;

        @ApiModelProperty(value = "交易员唯一标识码", example = "TRADER12345")
        private String uniqueCode;

        @ApiModelProperty(value = "胜率", example = "85%")
        private String winRatio;

        @ApiModelProperty(value = "带单天数", example = "90")
        private String leadDays;

        @ApiModelProperty(value = "交易员带单的合约列表", example = "[\"BTC-USDT\", \"ETH-USDT\"]")
        private List<String> traderInsts;

        @ApiModelProperty(value = "近90日交易员收益", example = "5000")
        private String pnl;

        @ApiModelProperty(value = "近90日交易员收益率", example = "25%")
        private String pnlRatio;

        @ApiModelProperty(value = "收益率数据", example = "[\"5%\", \"10%\", \"15%\"]")
        private List<String> pnlRatios;
    }

}