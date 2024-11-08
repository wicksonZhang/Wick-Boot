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
public enum MisfirePolicyEnum implements IntArrayValuable {

    EXECUTE_NOW(1, "立即执行"),

    EXECUTE_ONCE(2, "执行一次"),

    EXECUTION_WAIVER(3, "放弃执行")

    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MisfirePolicyEnum::getValue).toArray();

    private final Integer value;

    private final String label;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
