package com.wick.boot.module.system.enums.monitor;

import com.wick.boot.common.core.validator.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 计划执行错误策略枚举
 *
 * @author Wickson
 * @date 2024-11-08
 */
@Getter
@AllArgsConstructor
public enum JobGroupAddressTypeEnum implements IntArrayValuable {

    AUTOMATIC_ENTRY(0, "自动注册"),

    MANUAL_ENTRY(1, "手动录入")
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(JobGroupAddressTypeEnum::getValue).toArray();

    private final Integer value;

    private final String label;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
