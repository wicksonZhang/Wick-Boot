package com.wick.boot.module.okx.copy.trading.model.vo;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 交易员排名分页查询参数
 *
 * @author Wickson
 * @date 2024-12-04 09:26
 */
@Setter
@Getter
@ToString
@ApiModel(value = "PublicLedTradingQueryVO", description = "交易员排名分页查询参数")
public class PublicLedTradingQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "排序字段", example = "")
    private String sortField;

}
