package com.wick.boot.module.okx.market.model.dto;

import com.wick.boot.common.core.enums.CommonStatusEnum;
import com.wick.boot.common.core.validator.InEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 币种信息新增参数
 *
 * @author Wickson
 * @date 2024-11-21 23:14
 */
@Getter
@Setter
@ToString
@ApiModel(value = "MarketCoinAddVO", description = "币种信息新增参数")
public class MarketCoinAddVO {

    @ApiModelProperty(value = "产品类型", required = true, example = "SWAP")
    @NotBlank(message = "产品类型不能为空")
    private String instType;

    @ApiModelProperty(value = "产品编号", required = true, example = "BTC-USDT-SWAP")
    @NotBlank(message = "产品编号不能为空")
    private String instId;

}