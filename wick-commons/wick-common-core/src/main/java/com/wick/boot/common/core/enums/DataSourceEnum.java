package com.wick.boot.common.core.enums;

import com.wick.boot.common.core.validator.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 数据权限-枚举类
 *
 * @author ZhangZiHeng
 * @date 2024-04-29
 */
@Getter
@AllArgsConstructor
public enum DataSourceEnum implements IntArrayValuable {

    DATA_SCOPE_ALL(1, "全部数据权限"),

    DATA_SCOPE_CUSTOM(2, "自定数据权限"),

    DATA_SCOPE_DEPT(3, "部门数据权限"),

    DATA_SCOPE_DEPT_AND_CHILD(4, "部门及以下数据权限"),

    DATA_SCOPE_SELF(5, "仅本人数据权限"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DataSourceEnum::getValue).toArray();

    private final Integer value;

    private final String label;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
