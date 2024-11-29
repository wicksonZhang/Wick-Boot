package com.wick.boot.module.okx.market.model.vo.mycoin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 我的自选新增参数
 *
 * @author Wickson
 * @date 2024-11-29 17:42
 */
@Getter
@Setter
@ApiModel(value = "MarketMyCoinAddVO", description = "我的自选新增参数")
public class MarketMyCoinAddVO {

    @ApiModelProperty(value = "产品名称", required = true)
    @NotBlank(message = "产品名称不能为空")
    private String instId;

    @ApiModelProperty(value = "产品类型", required = true)
    @NotBlank(message = "产品类型不能为空")
    private String instType;

}


