package com.wick.boot.module.system.enums;

import com.wick.boot.common.core.validator.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 性别枚举
 *
 * @author Wickson
 * @date 2024-04-03
 */
@Getter
@AllArgsConstructor
public enum GenderTypeEnum implements IntArrayValuable {

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

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(GenderTypeEnum::getCode).toArray();

    /**
     * 根据状态码返回状态码描述的值
     *
     * @param code 性别Code
     * @return 性别名称
     */
    public static String valueOf(int code) {
        String sex = null;

        for (GenderTypeEnum genderTypeEnum : GenderTypeEnum.values()) {
            if (genderTypeEnum.getCode() == code) {
                sex = genderTypeEnum.getSex();
                break;
            }
        }
        return sex;
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
