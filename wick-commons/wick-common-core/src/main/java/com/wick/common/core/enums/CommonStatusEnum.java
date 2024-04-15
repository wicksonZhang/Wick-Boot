package com.wick.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 使用状态枚举
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum {

    ENABLE(1, "启用"),

    DISABLE(0, "禁用");

    private final Integer value;

    private final String label;

}
