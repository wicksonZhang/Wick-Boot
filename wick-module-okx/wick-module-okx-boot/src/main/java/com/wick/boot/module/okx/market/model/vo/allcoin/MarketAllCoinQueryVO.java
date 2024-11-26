package com.wick.boot.module.okx.market.model.vo.allcoin;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
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
@ApiModel(value = "MarketAllCoinQueryVO", description = "市场行情查询条件参数")
public class MarketAllCoinQueryVO extends CommonPageParamVO {

    /**
     * 产品类型（SPOT：币币、SWAP：永续合约、FUTURES：交割合约、OPTION：期权）
     */
    @ApiModelProperty(value = "产品类型", required = true, example = "SWAP")
    @NotBlank(message = "产品类型不能为空")
    private String instType;

    /**
     * 结算方式(USDT：USDT币结算、USD：美元结算、币本位)
     */
    @ApiModelProperty(value = "结算方式", required = true, example = "USDT")
    @NotBlank(message = "结算方式不能为空")
    private String billingMethod;

    /**
     * 币种名称
     */
    @ApiModelProperty(value = "币种名称", example = "BTC")
    private String ccy;

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
