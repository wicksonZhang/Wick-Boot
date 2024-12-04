package com.wick.boot.module.okx.model.vo.copy.trading;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 交易员排名-请求参数
 *
 * @author Wickson
 * @date 2024-12-04
 */
@Setter
@Getter
@ToString
public class PublicLeadTradersQueryVO {

    @ApiModelProperty(value = "产品类型", allowableValues = "SWAP", example = "SWAP", notes = "永续合约，默认值")
    private String instType;

    @ApiModelProperty(value = "排名类型", allowableValues = "overview,pnl,aum,win_ratio,pnl_ratio,current_copy_trader_pnl", example = "overview", notes = "综合排序，默认值")
    private String sortType;

    @ApiModelProperty(value = "交易员的状态", allowableValues = "0,1", example = "0", notes = "0: 所有交易员，包括有空缺和没有空缺; 1: 有空缺的交易员")
    private String state;

    @ApiModelProperty(value = "最短带单时长", allowableValues = "1,2,3,4", example = "1", notes = "1: 7 天; 2: 30 天; 3: 90 天; 4: 180 天")
    private String minLeadDays;

    @ApiModelProperty(value = "交易员资产范围的最小值 (单位: USDT)", example = "1000")
    private String minAssets;

    @ApiModelProperty(value = "交易员资产范围的最大值 (单位: USDT)", example = "10000")
    private String maxAssets;

    @ApiModelProperty(value = "带单规模的最小值 (单位: USDT)", example = "500")
    private String minAum;

    @ApiModelProperty(value = "带单规模的最大值 (单位: USDT)", example = "5000")
    private String maxAum;

    @ApiModelProperty(value = "排名数据的版本", example = "20231010182400", notes = "14 位数字，每10分钟生成一版，仅保留最新的5个版本，默认使用最近的版本。")
    private String dataVer;

    @ApiModelProperty(value = "查询页数", example = "1")
    private String page;

    @ApiModelProperty(value = "分页返回的结果集数量 (最大为20, 不填默认返回10条)", example = "10")
    private String limit;

}
