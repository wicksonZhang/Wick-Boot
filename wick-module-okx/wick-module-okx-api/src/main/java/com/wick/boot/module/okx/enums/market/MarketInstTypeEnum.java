package com.wick.boot.module.okx.enums.market;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 市场行情-产品类型枚举
 *
 * @author Wickson
 * @date 2024-11-21 22:32:28
 */
@Getter
@AllArgsConstructor
public enum MarketInstTypeEnum {

    /**
     * 币币
     */
    SPOT(0, "SPOT"),

    /**
     * 永续合约
     */
    SWAP(1, "SWAP"),

    /**
     * 交割合约
     */
    FUTURES(2, "FUTURES"),

    /**
     * 期权
     */
    OPTION(3, "OPTION");

    private final Integer code;

    private final String description;

}
