package com.wick.boot.module.system.enums;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.wick.boot.common.core.validator.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 菜单类型-枚举
 *
 * @author Wickson
 * @date 2024-04-02
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements IntArrayValuable {

    ROOT(0, "根节点"),

    MENU(1, "菜单"),

    CATALOG(2, "目录"),

    EXT_LINK(3, "外链"),

    BUTTON(4, "按钮"),
    ;

    /**
     * 类型
     */
    @EnumValue
    private final Integer value;

    /**
     * 名称
     */
    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(MenuTypeEnum::getValue).toArray();

    /**
     * 据键返回枚举-枚举类值
     *
     * @param value 键值
     * @return MenuTypeEnum
     */
    public static MenuTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(menuType -> menuType.getValue().equals(value), MenuTypeEnum.values());
    }


    @Override
    public int[] array() {
        return ARRAYS;
    }
}
