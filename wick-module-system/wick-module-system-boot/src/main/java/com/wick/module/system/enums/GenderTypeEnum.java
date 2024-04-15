package com.wick.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ZhangZiHeng
 * @date 2024-04-03
 */
@Getter
@AllArgsConstructor
public enum GenderTypeEnum {

    /**
     * 男
     */
    MALE(1, "男"),

    /**
     * 女
     */
    FEMALE(2, "女"),

    /**
     * 未知
     */
    UNKNOWN(3, "未知");

    /**
     * 性别Code
     */
    private final Integer code;

    /**
     * 性别名称
     */
    private final String sex;

}
