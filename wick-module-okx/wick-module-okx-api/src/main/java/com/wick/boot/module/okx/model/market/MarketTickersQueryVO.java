package com.wick.boot.module.okx.model.market;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 市场行情查询参数
 *
 * @author Wickson
 * @date 2024-11-19
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MarketTickersQueryVO", description = "市场行情查询条件参数")
public class MarketTickersQueryVO {

    /**
     * 产品类型（SPOT：币币、SWAP：永续合约、FUTURES：交割合约、OPTION：期权）
     */
    @ApiModelProperty(value = "产品类型", required = true, example = "SWAP")
    @NotBlank(message = "产品类型不能为空")
    private String instType;

    /**
     * 币的指数（适用于交割/永续/期权，如 BTC-USD）
     */
    @ApiModelProperty(value = "币的指数", example = "BTC-USD")
    private String uly;

    /**
     * 交易品种（适用于交割/永续/期权，如 BTC-USD）
     */
    @ApiModelProperty(value = "交易品种", example = "BTC-USD")
    private String instFamily;

}
