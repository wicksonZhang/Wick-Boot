package com.wick.boot.module.okx.market.model.vo;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 市场行情查询参数
 *
 * @author Wickson
 * @date 2024-11-19
 */
@Setter
@Getter
@ApiModel(value = "TickersQueryVO", description = "市场行情查询条件参数")
public class MarketTickersQueryVO extends CommonPageParamVO {

    /**
     * 产品类型（SPOT：币币、SWAP：永续合约、FUTURES：交割合约、OPTION：期权）
     */
    @ApiModelProperty(value = "产品类型", required = true, example = "SWAP")
    @NotBlank(message = "产品类型不能为空")
    private String instType;

    /**
     * 币的指数（适用于交割/永续/期权，如 BTC-USD）
     */
    @ApiModelProperty(value = "币的指数", example = "BTC-USDT")
    private String uly;

    /**
     * 交易品种（适用于交割/永续/期权，如 BTC-USD）
     */
    @ApiModelProperty(value = "交易品种", example = "BTC-USDT")
    private String instFamily;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段", example = "instType")
    private String sortField;

    /**
     * 排序方式：ASC/DESC
     */
    @ApiModelProperty(value = "排序字段", example = "descending")
    private String sortOrder;

}
