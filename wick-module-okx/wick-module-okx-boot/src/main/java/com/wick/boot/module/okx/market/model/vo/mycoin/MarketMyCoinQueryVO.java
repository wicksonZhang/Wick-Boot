package com.wick.boot.module.okx.market.model.vo.mycoin;

import com.wick.boot.common.core.model.vo.CommonPageParamVO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 我的自选分页查询参数
 *
 * @author Wickson
 * @date 2024-12-02 11:04
 */
@Setter
@Getter
@ApiModel(value = "MarketMyCoinQueryVO", description = "我的自选分页查询参数")
public class MarketMyCoinQueryVO extends CommonPageParamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 币种名称
     */
    private String coinName;

}
