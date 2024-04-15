package com.wick.boot.common.core.enums;

import com.wick.boot.common.core.validator.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 使用状态枚举
 *
 * @author ZhangZiHeng
 * @date 2024-04-07
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements IntArrayValuable {

    ENABLE(1, "启用"),

    DISABLE(0, "禁用");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonStatusEnum::getValue).toArray();

    private final Integer value;

    private final String label;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
