package cn.wickson.security.system.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型-枚举
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    ROOT(0L, "根节点"),

    MENU(1L, "菜单"),

    CATALOG(2L, "目录"),

    EXT_LINK(3L, "外链"),

    BUTTON(4L, "按钮"),
    ;

    /**
     * 类型
     */
    private final Long value;

    /**
     * 名称
     */
    private final String name;

    /**
     * 据键返回枚举-枚举类值
     *
     * @param value 键值
     * @return MenuTypeEnum
     */
    public static MenuTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(menuType -> menuType.getValue().equals(value), MenuTypeEnum.values());
    }


}
