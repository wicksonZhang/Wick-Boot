package com.wick.boot.module.okx.enums.market;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 排序类型枚举
 *
 * @author Wickson
 * @date 2024-11-26
 */
@Getter
@AllArgsConstructor
public enum MarketSortTypeEnum {

    /**
     * 降序
     */
    DESC("descending", "DESC"),

    /**
     * 升序
     */
    ASC("ascending", "ASC"),
    ;

    private final String code;

    private final String description;

    /**
     * 据状态码返回状态码描述的值
     *
     * @param code
     * @return
     */
    public static String getValue(String code) {
        String description = null;
        for (MarketSortTypeEnum companyType : MarketSortTypeEnum.values()) {
            if (Objects.equals(companyType.getCode(), code)) {
                description = companyType.getDescription();
                break;
            }
        }
        return description;
    }
}
