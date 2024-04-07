package cn.wickson.security.system.model.entity;

import cn.wickson.security.commons.model.entity.BaseDO;
import cn.wickson.security.system.enums.MenuTypeEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统管理 - 菜单信息
 *
 * @author ZhangZiHeng
 * @date 2024-04-02
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_menu")
public class SystemMenu extends BaseDO {

    @TableId
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单类型(1:菜单；2：目录；3：外链；4：按钮)
     */
    private MenuTypeEnum type;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由path
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 按钮权限标识
     */
    private String perm;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(0:禁用;1:开启)
     */
    private Integer visible;

    /**
     * 跳转路径
     */
    private String redirect;

}
